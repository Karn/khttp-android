/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package khttp

import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals


@RunWith(RobolectricTestRunner::class)
class KHttpHeadTest : KHttpTestBase() {

    @Test
    @Ignore
    fun validateResponse() {
        val response = head(url = "https://httpbin.org/get")

        assertEquals(200, response.statusCode)
    }

    @Test
    fun redirectWithRedirectsDisallowed() {
        val response = head(url = "https://httpbin.org/redirect/2")

        assertEquals(302, response.statusCode)
    }

    @Test
    @Ignore
    fun redirect() {
        val response = head(url = "https://httpbin.org/redirect/2",
                allowRedirects = true)

        assertEquals(200, response.statusCode)
    }
}
