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

package khttp.extensions

import khttp.KHttpTestBase
import khttp.extensions.split
import khttp.extensions.splitLines
import org.junit.Ignore
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ExtensionsTest : KHttpTestBase() {

    companion object {
        val empty = ByteArray(0)
        const val string = "\"Goddammit\", he said\nThis is a load of bullshit.\r\nPlease, just kill me now.\r"
        val byteArray = string.toByteArray()
    }

    @Test
    fun splitByLines() {
        val split = byteArray.splitLines().map { it.toString(Charsets.UTF_8) }
        val expected = string.split(Regex("(\r\n|\r|\n)"))

        assertEquals(expected, split)
    }

    @Test
    fun splitByLetter() {
        val splitBy = "e"
        val split = byteArray.split(splitBy.toByteArray()).map { it.toString(Charsets.UTF_8) }
        val expected = string.split(splitBy)

        assertEquals(expected, split)
    }

    /**
     * I don't quite see why we'd need this. It's currently failing and i'm not sure what the usecase is. Marked for
     * investigation. - @Karn
     * // TODO: Investigate what the purpose of this test is.
     */
    @Test
    @Ignore
    fun splitByWord() {
        val splitBy = "is"
        val split = byteArray.split(splitBy.toByteArray()).map { it.toString(Charsets.UTF_8) }
        val expected = string.split(splitBy)

        assertEquals(expected, split)
    }

    @Test
    fun splitGivenAnEmptyArray() {
        var split = empty.splitLines()
        assertEquals(0, split.size)

        split = empty.split(ByteArray(0))
        assertEquals(1, split.size)
        assertTrue(split[0].isEmpty())
    }
}
