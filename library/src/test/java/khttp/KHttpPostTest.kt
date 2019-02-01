/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package khttp

import khttp.extensions.fileLike
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.File
import java.util.Base64
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
class KHttpPostTest : KHttpTestBase() {

    @Test
    fun postRawData() {
        var response = post(url = "http://httpbin.org/post",
                data = "Hello, world!")

        assertEquals("Hello, world!", response.jsonObject.getString("data"))

        response = post(url = "http://httpbin.org/post",
                data = "a=b&c=d")
        assertEquals("a=b&c=d", response.jsonObject.getString("data"))
    }

    @Test
    fun withFormData() {
        val response = post(url = "http://httpbin.org/post",
                data = mapOf("a" to "b", "c" to "d"))

        val form = response.jsonObject.getJSONObject("form")
        assertEquals("b", form.getString("a"))
        assertEquals("d", form.getString("c"))
    }

    /**
     * Need to clearly define what is accepted as a json input. It should just be a JSON string.
     */
    @Test
    @Ignore
    fun withJsonData() {
        val jsonMap = mapOf("books" to listOf(mapOf("title" to "Pride and Prejudice", "author" to "Jane Austen")))
        val response = post(url = "http://httpbin.org/post",
                json = jsonMap)

        val returnedJSON = response.jsonObject.getJSONObject("json")
        val returnedBooks = returnedJSON.getJSONArray("books")

        assertEquals(jsonMap.size, returnedJSON.length())
        assertEquals(jsonMap["books"]!!.size, returnedBooks.length())

        val firstBook = jsonMap["books"]!![0]
        val firstReturnedBook = returnedBooks.getJSONObject(0)
        assertEquals(firstBook["title"], firstReturnedBook.getString("title"))
        assertEquals(firstBook["author"], firstReturnedBook.getString("author"))
    }

    @Test
    fun withListJsonData() {
        val jsonList = listOf("A thing", "another thing")
        val response = post(url = "https://httpbin.org/post",
                json = jsonList)

        val returnedJSON = response.jsonObject.getJSONArray("json")
        assertEquals(jsonList[0], returnedJSON.getString(0))
        assertEquals(jsonList[1], returnedJSON.getString(1))
    }

    @Test
    fun withArrayListJsonData() {
        val jsonArray = arrayListOf("A thing", "another thing")
        val response = post(url = "https://httpbin.org/post",
                json = jsonArray)

        val returnedJSON = response.jsonObject.getJSONArray("json")
        assertEquals(jsonArray[0], returnedJSON.getString(0))
        assertEquals(jsonArray[1], returnedJSON.getString(1))
    }

    @Test
    fun withJsonObjectData() {
        val jsonObject = JSONObject("""{"valid": true}""")
        val response = post(url = "https://httpbin.org/post",
                json = jsonObject)

        val returnedJSON = response.jsonObject.getJSONObject("json")
        assertTrue(returnedJSON.getBoolean("valid"))
    }

    @Test
    fun withJsonArrayData() {
        val jsonObject = JSONArray("[true]")
        val response = post(url = "https://httpbin.org/post",
                json = jsonObject)

        val returnedJSON = response.jsonObject.getJSONArray("json")
        assertTrue(returnedJSON.getBoolean(0))
    }

    @Test
    fun withInvalidJson() {
        assertFailsWith(IllegalArgumentException::class) {
            post(url = "https://httpbin.org/post",
                    json = object {})
        }
    }

    @Test
    fun fileUploadWithoutFormParameters() {
        val file = "hello".fileLike("derp")
        val response = post(url = "https://httpbin.org/post",
                files = listOf(file))

        val files = response.jsonObject.getJSONObject("files")
        assertEquals(1, files.length())
        assertNotNull(files.optString(file.fieldName, null))
        assertEquals(file.contents.toString(Charsets.UTF_8), files.optString(file.fieldName))
    }

    @Test
    fun fileUploadWithFormParameters() {
        val file = "hello".fileLike("derp")
        val params = mapOf("top" to "kek")
        val response = post(url = "https://httpbin.org/post",
                files = listOf(file),
                data = params)

        val files = response.jsonObject.getJSONObject("files")
        val form = response.jsonObject.getJSONObject("form")

        assertEquals(1, files.length())
        assertNotNull(files.optString(file.fieldName, null))
        assertEquals(file.contents.toString(Charsets.UTF_8), files.optString(file.fieldName))
        assertEquals(1, form.length())
        assertNotNull(form.optString("top", null))
        assertEquals("kek", form.optString("top"))
    }

    @Test
    fun streamingFileUpload() {
        // Get our file to stream (a beautiful rare pepe)
        val file = File("src/test/res/rarest_of_pepes.png")
        val response = post(url = "https://httpbin.org/post",
                data = file)

        val data = response.jsonObject.getString("data")
        assertTrue(data.startsWith("data:application/octet-stream;base64,"))

        val base64 = data.split("data:application/octet-stream;base64,")[1]
        val rawData = Base64.getDecoder().decode(base64)
        assertEquals(file.readBytes().asList(), rawData.asList())
    }

    @Test
    fun streamingInputStreamUpload() {
        val file = File("src/test/res/rarest_of_pepes.png")
        val inputStream = file.inputStream()
        val response = post(url = "https://httpbin.org/post",
                data = inputStream)

        val data = response.jsonObject.getString("data")
        assertTrue(data.startsWith("data:application/octet-stream;base64,"))

        val base64 = data.split("data:application/octet-stream;base64,")[1]
        val rawData = Base64.getDecoder().decode(base64)
        assertEquals(file.readBytes().asList(), rawData.asList())
    }

    @Test
    fun withJson() {
        val expected = """{"test":true}"""
        val response = post(url = "https://httpbin.org/post",
                json = mapOf("test" to true))

        assertEquals(expected, response.request.body.toString(Charsets.UTF_8))
    }
}
