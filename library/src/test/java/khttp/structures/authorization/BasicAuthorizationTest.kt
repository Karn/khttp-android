/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package khttp.structures.authorization

import android.util.Base64
import khttp.KHttpTestBase
import org.junit.Test
import kotlin.test.assertEquals

class BasicAuthorizationTest : KHttpTestBase() {

    @Test
    fun validateBasicAuth() {
        val username = "test"
        val password = "hunter2"
        val base64 = "Basic " + Base64.encode("$username:$password".toByteArray(), Base64.DEFAULT).toString(Charsets.UTF_8)
        val auth = BasicAuthorization(username, password)

        assertEquals(username, auth.user)
        assertEquals(password, auth.password)

        val (header, value) = auth.header
        assertEquals("Authorization", header)
        assertEquals(base64, value)
    }
}
