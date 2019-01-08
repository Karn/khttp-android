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

package khttp.structures.authorization

import khttp.KHttpTestBase
import khttp.structures.authorization.BasicAuthorization
import org.junit.Test
import java.util.Base64
import kotlin.test.assertEquals

class BasicAuthorizationTest : KHttpTestBase() {

    @Test
    fun validateBasicAuth() {
        val username = "test"
        val password = "hunter2"
        val base64 = "Basic " + Base64.getEncoder().encode("$username:$password".toByteArray()).toString(Charsets.UTF_8)
        val auth = BasicAuthorization(username, password)

        assertEquals(username, auth.user)
        assertEquals(password, auth.password)

        val (header, value) = auth.header
        assertEquals("Authorization", header)
        assertEquals(base64, value)
    }
}
