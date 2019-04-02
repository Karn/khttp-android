/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package khttp

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals


@RunWith(RobolectricTestRunner::class)
class KHttpHeadTest : KHttpTestBase() {

    @Test
    fun validateResponse() {
        val response = head(url = "https://httpbin.org/status/200")

        assertEquals(200, response.statusCode)
    }

    @Test
    fun redirectWithRedirectsDisallowed() {
        val response = head(url = "https://httpbin.org/redirect-to?url=https://httpbin.org/status/200")

        assertEquals(302, response.statusCode)
    }

    @Test
    fun redirect() {
        val response = head(url = "https://httpbin.org/redirect-to?url=https://httpbin.org/status/200",
                allowRedirects = true)

        assertEquals(200, response.statusCode)
    }
}
