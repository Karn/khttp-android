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

package khttp.structures.parameters

import khttp.KHttpTestBase
import khttp.structures.parameters.Parameters
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ParametersSpec : KHttpTestBase() {

    @Test
    fun fromEmptyMap() {
        val params = Parameters(mapOf())

        assertTrue(params.isEmpty())
        assertTrue(params.toString().isEmpty())
    }

    @Test
    fun fromNonEmptyMap() {
        val map = mapOf("test" to "value", "jest" to "lalue")
        val params = Parameters(map)

        assertEquals(2, params.size)
        assertTrue("test" in params)
        assertTrue("jest" in params)
        assertTrue(params.containsValue("value"))
        assertTrue(params.containsValue("lalue"))

        assertEquals("value", params["test"])
        assertEquals("lalue", params["jest"])
        assertEquals("test=value&jest=lalue", params.toString())

        assertFalse(params.containsKey(null as String?))
        assertNull(params[null as String?])
        assertFalse(params.containsValue(null as String?))
    }
}
