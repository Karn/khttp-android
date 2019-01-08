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

package khttp.structures.files

import khttp.KHttpTestBase
import khttp.extensions.fileLike
import org.junit.Ignore
import org.junit.Test
import java.io.File
import java.nio.file.Paths
import kotlin.test.assertEquals

class FileLikeTest : KHttpTestBase() {

    companion object {
        const val PATH = "src/test/res/rarest_of_pepes.png"
        const val NAME = "rarest_of_pepes.png"

        val file = File(PATH)
        val path = Paths.get(PATH)
    }

    @Test
    fun fileWithoutCustomName() {
        val fileLike = file.fileLike()
        assertEquals(NAME, fileLike.fieldName)
        assertEquals(file.readBytes().asList(), fileLike.contents.asList())
    }

    @Test
    fun fileWithCustomName() {
        val name = "not_rare_pepe.png"
        val fileLike = file.fileLike(name = name)
        assertEquals(name, fileLike.fieldName)
        assertEquals(file.readBytes().asList(), fileLike.contents.asList())
    }

    @Test
    fun fileFromPathWithoutCustomName() {
        val fileLike = path.fileLike()
        assertEquals(NAME, fileLike.fieldName)
        assertEquals(path.toFile().readBytes().asList(), fileLike.contents.asList())
    }

    @Test
    fun fileFromPathWithCustomName() {
        val name = "not_rare_pepe.png"
        val fileLike = path.fileLike(name = name)
        assertEquals(name, fileLike.fieldName)
        assertEquals(path.toFile().readBytes().asList(), fileLike.contents.asList())
    }

    @Test
    fun fileFromString() {
        val string = "toppest of keks"
        val name = "not_rare_pepe.png"
        val fileLike = string.fileLike(name = name)
        assertEquals(name, fileLike.fieldName)
        assertEquals(string.toByteArray().asList(), fileLike.contents.asList())
    }
}
