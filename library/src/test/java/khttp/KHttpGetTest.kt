/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package khttp

import khttp.get
import khttp.structures.authorization.BasicAuthorization
import org.json.JSONObject
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.net.MalformedURLException
import java.net.SocketTimeoutException
import java.util.zip.GZIPInputStream
import java.util.zip.InflaterInputStream
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
class KHttpGetTest : KHttpTestBase() {

    @Test
    fun validateResponse() {

        val url = "http://httpbin.org/range/26"
        val response = get(url = url)

        assertEquals("abcdefghijklmnopqrstuvwxyz", response.text, "The text of the result should equal the alphabet in lowercase.")
        assertEquals(url, response.url, "The URL of the response object should be the initial URL.")
        assertEquals(200, response.statusCode, "The request should complete with a 200 status code")
        assertEquals("<Response [200]>", response.toString(), "The string repr. of the response should be built properly.")
    }

    @Test
    fun validateParameters() {
        val response = get(url = "http://httpbin.org/get",
                params = mapOf("a" to "b", "c" to "d"))

        val args = response.jsonObject.getJSONObject("args")
        assertEquals("b", args.getString("a"))
        assertEquals("d", args.getString("c"))
    }

    @Test
    fun basicAuth() {
        val response = get(url = "http://httpbin.org/basic-auth/khttp/isawesome",
                auth = BasicAuthorization("khttp", "isawesome"))

        assertTrue(response.jsonObject.getBoolean("authenticated"))
        assertEquals("khttp", response.jsonObject.getString("user"))
    }


    @Test
    fun cookies() {
        val response = get(url = "http://httpbin.org/cookies",
                cookies = mapOf("test" to "success"))

        val cookies = response.jsonObject.getJSONObject("cookies")
        assertEquals("success", cookies.getString("test"))
    }

    @Test
    fun redirects() {
        val response = get(url = "http://httpbin.org/redirect-to?url=http://httpbin.org/get")

        assertEquals("http://httpbin.org/get", response.jsonObject.getString("url"))
    }

    @Test
    fun redirectsWith307() {
        val response = get(url = "http://httpbin.org/redirect-to?status_code=307&url=http://httpbin.org/get")

        assertEquals("http://httpbin.org/get", response.jsonObject.getString("url"))
    }

    @Test
    fun redirectsWith308() {
        val response = get(url = "http://httpbin.org/redirect-to?status_code=308&url=http://httpbin.org/get")

        assertEquals("http://httpbin.org/get", response.jsonObject.getString("url"))
    }

    @Test
    fun redirectsWithRedirectsDisallowed() {
        val response = get(url = "http://httpbin.org/redirect-to?url=http://httpbin.org/get",
                allowRedirects = false)

        assertEquals(302, response.statusCode)
    }

    @Test
    fun redirectsWith307WithRedirectsDisallowed() {
        val response = get(url = "http://httpbin.org/redirect-to?status_code=307&url=http://httpbin.org/get",
                allowRedirects = false)

        assertEquals(307, response.statusCode)
    }

    @Test
    fun redirectsWith308WithRedirectsDisallowed() {
        val response = get(url = "http://httpbin.org/redirect-to?status_code=308&url=http://httpbin.org/get",
                allowRedirects = false)

        assertEquals(308, response.statusCode)
    }

    @Test
    fun multipleRedirects() {
        val response = get(url = "http://httpbin.org/redirect/5")

        assertEquals("http://httpbin.org/get", response.jsonObject.getString("url"))
    }

    @Test
    fun timeout() {
        assertFailsWith(SocketTimeoutException::class) {
            get(url = "http://httpbin.org/delay/10",
                    timeout = 1.0)
        }
    }

    @Test
    fun setCookiesWithoutRedirects() {
        val cookieName = "test"
        val cookieValue = "quite"
        val response = get(url = "http://httpbin.org/cookies/set?$cookieName=$cookieValue",
                allowRedirects = false)

        val cookies = response.cookies
        assertEquals(1, cookies.size)

        val cookie = cookies.getCookie(cookieName)
        val text = cookies[cookieName]

        assertNotNull(cookie)
        assertNotNull(text)
        assertEquals(cookieValue, cookie.value)
        assertEquals(cookieValue, text.toString().split(";")[0])
    }

    @Test
    fun setCookieWithRedirects() {
        val cookieName = "test"
        val cookieValue = "quite"
        val response = get(url = "http://httpbin.org/cookies/set?$cookieName=$cookieValue")

        val cookies = response.cookies
        assertEquals(1, cookies.size)

        val cookie = cookies.getCookie(cookieName)
        val text = cookies[cookieName]
        assertNotNull(cookie)
        assertNotNull(text)
        assertEquals(cookieValue, cookie.value)
        // Attributes ignored
        assertEquals(cookieValue, text.toString().split(";")[0])
    }

    @Test
    fun multipleCookiesWithRedirects() {
        val cookieNameOne = "test"
        val cookieValueOne = "quite"
        val cookieNameTwo = "derp"
        val cookieValueTwo = "herp"
        val response = get(url = "http://httpbin.org/cookies/set?$cookieNameOne=$cookieValueOne&$cookieNameTwo=$cookieValueTwo")

        val cookies = response.cookies
        assertEquals(2, cookies.size)

        val cookie = cookies.getCookie(cookieNameOne)
        val text = cookies[cookieNameOne]

        assertNotNull(cookie)
        assertNotNull(text)
        assertEquals(cookieValueOne, cookie.value)
        assertEquals(cookieValueOne, text.toString().split(";")[0])

        val cookieTwo = cookies.getCookie(cookieNameTwo)
        val textTwo = cookies[cookieNameTwo]

        assertNotNull(cookieTwo)
        assertNotNull(textTwo)
        assertEquals(cookieValueTwo, cookieTwo.value)
        assertEquals(cookieValueTwo, textTwo.toString().split(";")[0])
    }

    @Test
    fun gzip() {
        val response = get(url = "https://httpbin.org/gzip")

        assertTrue(response.raw is GZIPInputStream)
        assertTrue(response.jsonObject.getBoolean("gzipped"))
    }

    @Test
    fun deflate() {
        val response = get(url = "https://httpbin.org/deflate")

        assertTrue(response.raw is InflaterInputStream)
        assertTrue(response.jsonObject.getBoolean("deflated"))
    }

    @Test
    fun requestWith418() {
        val response = get(url = "https://httpbin.org/status/418")

        assertEquals(418, response.statusCode)
        assertTrue(response.text.contains("teapot"))
    }

    fun requestForUTF8Document() {
        val response = get(url = "https://httpbin.org/encoding/utf8")

        assertEquals(Charsets.UTF_8, response.encoding)
        assertTrue(response.text.contains("∮"))
    }

    @Test
    fun unsupportedSchema() {
        assertFailsWith(IllegalArgumentException::class) {
            get(url = "ftp://google.com")
        }
        assertFailsWith(MalformedURLException::class) {
            get(url = "gopher://google.com")
        }
    }

    @Test
    fun userAgent() {
        val userAgent = "khttp/test"
        val response = get(url = "https://httpbin.org/user-agent",
                headers = mapOf("User-Agent" to userAgent))

        assertEquals(userAgent, response.jsonObject.getString("user-agent"))
    }

    @Test
    fun requestWithAPort() {
        val response = get(url = "https://httpbin.org:443/get")

        assertNotNull(response.jsonObject)
    }

    @Test
    fun responseWithJsonArray() {
        val response = get(url = "http://jsonplaceholder.typicode.com/users")

        assertEquals(10, response.jsonArray.length())
    }

    @Test
    fun nonStreamingRequest() {
        val response = get(url = "https://httpbin.org/get")

        assertEquals(0, response.raw.available())
    }

    @Test
    fun streamingRequest() {
        val response = get(url = "https://httpbin.org/get",
                stream = true)

        assertTrue(response.raw.available() > 0)
    }

    @Test
    fun streamingRequestWithStreamingLineResponse() {
        val response = get(url = "http://httpbin.org/stream/4",
                stream = true)

        val iterator = response.lineIterator()
        var counter = 0
        for (line in iterator) {
            val json = JSONObject(line.toString(response.encoding))
            assertEquals(counter++, json.getInt("id"))
        }

        assertEquals(4, counter)
    }

    @Test
    @Ignore
    fun streamingRequestWithStreamingByteResponse() {
        val response = get(url = "http://httpbin.org/stream-bytes/4?seed=1",
                stream = true)

        val iterator = response.contentIterator(chunkSize = 1)
        var counter = 0
        val expected = byteArrayOf(0x22, 0xD8.toByte(), 0xC3.toByte(), 0x41)
        for (byte in iterator) {
            assertEquals(1, byte.size)
            assertEquals(expected[counter++], byte[0])
        }

        assertEquals(4, counter)
    }

    @Test
    @Ignore
    fun streamingRequestWithoutEvenLines() {
        val url = "https://httpbin.org/bytes/1690?seed=1"
        val response = get(url = url,
                stream = true)

        val iterator = response.lineIterator()
        val bytes = iterator.asSequence().toList().flatMap { it.toList() }
        val contentWithoutBytes = get(url).content.toList().filter { it != '\r'.toByte() && it != '\n'.toByte() }

        assertEquals(contentWithoutBytes, bytes)
    }

    @Test
    fun requestWithSpaceInUrl() {
        val url = "https://httpbin.org/anything/some text"
        val response = get(url)

        assertEquals(url, response.jsonObject.getString("url"))
    }
}
