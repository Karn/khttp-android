/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package io.karn.khttp.structures.maps

import io.karn.khttp.KHttpTestBase
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

class CaseInsensitiveMapTest : KHttpTestBase() {

    companion object {
        val backingMap = hashMapOf("a" to "b", "c" to "d")
        val caseInsensitiveMap = CaseInsensitiveMap(backingMap)
    }

    @Test
    fun validateInitialization() {
        val backed = backingMap["a"]
        val lower = caseInsensitiveMap["a"]
        val upper = caseInsensitiveMap["A"]
        assertEquals(backed, lower)
        assertEquals(backed, upper)
        assertEquals(upper, lower)
    }

    @Test
    fun validateMapData() {
        val backed = backingMap.containsKey("c")
        val lower = caseInsensitiveMap.containsKey("c")
        val upper = caseInsensitiveMap.containsKey("C")

        assertEquals(backed, lower)
        assertEquals(backed, upper)
        assertEquals(upper, lower)
    }

    @Test
    fun validateToString() {
        val backing = backingMap.toString()
        val insensitive = caseInsensitiveMap.toString()

        assertEquals(backing, insensitive)
    }

    @Test
    fun invalidMappings() {
        assertFalse(caseInsensitiveMap.containsKey(null as String?))
        assertFalse(caseInsensitiveMap.containsKey(object {} as Any))
        assertNull(caseInsensitiveMap.get(null as String?))
        assertNull(caseInsensitiveMap.get(object {} as Any))
        assertFalse(caseInsensitiveMap.containsKey("b"))
        assertFalse(caseInsensitiveMap.containsKey("B"))
    }

    @Test
    fun emptyBackingMap() {
        val emptyBackingMap = hashMapOf<String, String>()
        val emptyCaseInsensitiveMap = CaseInsensitiveMap(emptyBackingMap)
        assertFalse(emptyCaseInsensitiveMap.containsKey(null as String?))
        assertFalse(emptyCaseInsensitiveMap.containsKey(object {} as Any))
    }
}
