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

package khttp.structures.cookie

import khttp.KHttpTestBase
import khttp.structures.cookie.Cookie
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CookieTest : KHttpTestBase() {

    @Test
    fun cookieAsString() {
        val key = "password"
        val value = "hunter2"
        val cookieString = "$key=$value; Path=/"
        val cookie = Cookie(cookieString)

        assertEquals(key, cookie.key)
        assertEquals(value, cookie.value)
        assertEquals("$value; Path=/", cookie.valueWithAttributes)

        val attributes = cookie.attributes
        assertEquals(1, attributes.size)
        assertTrue("Path" in attributes)
        assertEquals("/", attributes["Path"])
    }

    @Test
    fun cookieAsStringWithAttributeWithoutValue() {
        val key = "password"
        val value = "hunter2"
        val cookieString = "$key=$value; Path=/; Awesome"
        val cookie = Cookie(cookieString)

        assertEquals("$value; Path=/; Awesome", cookie.valueWithAttributes)

        val attributes = cookie.attributes
        assertEquals(2, attributes.size)
        assertTrue("Path" in attributes)
        assertEquals("/", attributes["Path"])
        assertTrue("Awesome" in attributes)
        assertNull(attributes["Awesome"])
    }

    @Test
    fun invalidCookie() {
        assertFailsWith(IllegalArgumentException::class) {
            Cookie("wow")
        }
    }
}
