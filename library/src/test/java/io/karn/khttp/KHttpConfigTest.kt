/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package io.karn.khttp

import org.junit.Test
import kotlin.test.assertEquals

class KHttpConfigTest : KHttpTestBase() {


    @Test
    fun validateInterceptorAttach() {
        KHttpConfig.attachInterceptor {
            System.out.println(it.statusCode)
        }

        assertEquals(1, KHttpConfig.interceptors.size)
    }
}