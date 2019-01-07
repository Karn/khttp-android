/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package io.karn.khttp.structures.cookie

import io.karn.khttp.KHttpTestBase
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CookieJarTest : KHttpTestBase() {

    @Test
    fun validate() {
        val cookie1 = Cookie("test1", "value1")
        val cookie2 = Cookie("test2", "value2", mapOf("attr1" to "attrv1"))
        val cookies = listOf(cookie1, cookie2)
        val cookieJar = CookieJar(*cookies.toTypedArray())

        assertEquals(2, cookieJar.size)

        var cookie = cookieJar.getCookie("test1")
        assertNotNull(cookie)
        assertEquals("test1", cookie.key)
        assertEquals("value1", cookie.value)
        assertEquals(0, cookie.attributes.size)

        assertTrue("test1" in cookieJar)
        assertFalse("test3" in cookieJar)
        assertFalse(cookieJar.containsKey(null as String?))

        assertTrue(cookieJar.containsValue(cookie1.valueWithAttributes))
        assertFalse(cookieJar.containsValue(""))
        assertFalse(cookieJar.containsValue(null as String?))

        cookie = cookieJar.getCookie("test2")
        assertNotNull(cookie)
        assertEquals("test2", cookie.key)
        assertEquals("value2", cookie.value)
        assertEquals(mapOf("attr1" to "attrv1"), cookie.attributes)

        cookie = cookieJar.getCookie("test3")
        val cookieRaw: Any? = cookieJar[null as String?]
        assertNull(cookie)
        assertNull(cookieRaw)

        var cookieValue = cookieJar["test1"]
        assertNotNull(cookieValue)
        assertEquals(cookie1.valueWithAttributes, cookieValue)

        cookieValue = cookieJar["test3"]
        assertNull(cookieValue)

        cookie = Cookie("delicious", "cookie", mapOf("edible" to "damn straight"))
        cookieJar.setCookie(cookie)
        assertEquals(3, cookieJar.size)
        val added = cookieJar.getCookie("delicious")
        assertEquals(added, cookie)
    }

    @Test
    fun validateCookieJarConstructedWithMap() {
        val cookies = mapOf("test1" to "value1", "test2" to "value2; attr1=attrv1")
        val cookieJar = CookieJar(cookies)

        assertEquals(2, cookieJar.size)

        var cookie = cookieJar.getCookie("test1")
        assertNotNull(cookie)
        assertEquals("test1", cookie.key)
        assertEquals("value1", cookie.value)
        assertEquals(0, cookie.attributes.size)

        cookie = cookieJar.getCookie("test2")
        assertNotNull(cookie)
        assertEquals("test2", cookie!!.key)
        assertEquals("value2", cookie!!.value)
        assertEquals(mapOf("attr1" to "attrv1"), cookie!!.attributes)

        cookie = cookieJar.getCookie("test3")
        assertNull(cookie)

        cookie = Cookie("delicious", "cookie", mapOf("edible" to "damn straight"))
        cookieJar.setCookie(cookie)
        assertEquals(3, cookieJar.size)
        val added = cookieJar.getCookie("delicious")
        assertEquals(added, cookie)

        var originalSize = cookieJar.size
        cookieJar.remove("delicious")
        val cookieValue = cookieJar["delicious"]
        assertEquals(originalSize - 1, cookieJar.size)
        assertNull(cookieValue)

        originalSize = cookieJar.size
        val removed: Any? = (cookieJar as MutableMap<Any?, String>).remove(null)
        assertEquals(originalSize, cookieJar.size)
        assertNull(removed)
    }
}
