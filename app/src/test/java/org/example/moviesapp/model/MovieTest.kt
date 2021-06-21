@file:Suppress("PrivatePropertyName", "PrivatePropertyName")

package org.example.moviesapp.model

import junit.framework.TestCase
import org.junit.Test

class MovieTest : TestCase(){

    private var SUT: Movie? = null

    @Test
    fun testMovie_withArray_abcde(){
        val candidates = listOf("a", "b", "c", "d", "e", "f")
        val expecteds =  listOf("a", "b", "c", "d", "e", "f")
        testCandidates(candidates, expecteds)
    }

    @Test fun testMovie_withArray_empty(){
        val candidates = listOf("", "", "", "", "", "")
        val expecteds =  listOf("", "", "", "", "", "")
        testCandidates(candidates, expecteds)
    }


//    @Test fun testMovie_withArrayNull(){
//        val candidates = listOf(null, null, null, null, null, null)
//        val expecteds =  listOf(null, null, null, null, null, null)
//        testCandidates(candidates, expecteds)// NOT COMPILE
//    }

    private fun testCandidates(candidates: List<String>,
                               expecteds: List<String>) {
        SUT = Movie(candidates[0], candidates[1], candidates[2],
                candidates[3], candidates[4], candidates[5])

        assertEquals(expecteds[0], SUT?.originalTitle)
        assertEquals(expecteds[1], SUT?.posterPath)
        assertEquals(expecteds[2], SUT?.genre)
        assertEquals(expecteds[3], SUT?.releaseDate)
        assertEquals(expecteds[4], SUT?.overview)
        assertEquals(expecteds[5], SUT?.originalLanguage)
    }

    @Test
    fun testMovie(){
        val originalTitle = ""
        val posterPath = ""
        val genre = ""
        val releaseDate = ""
        val overview = ""
        val originalLanguage = ""

        SUT = Movie(originalTitle, posterPath,
                genre, releaseDate, overview, originalLanguage)

        assertEquals("", SUT?.originalTitle)
        assertEquals("", SUT?.posterPath)
        assertEquals("", SUT?.genre)
        assertEquals("", SUT?.releaseDate)
        assertEquals("", SUT?.overview)
        assertEquals("", SUT?.originalLanguage)
    }

}