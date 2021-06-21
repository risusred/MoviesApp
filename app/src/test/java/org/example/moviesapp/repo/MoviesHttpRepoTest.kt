@file:Suppress("PrivatePropertyName")

package org.example.moviesapp.repo

import junit.framework.TestCase

class MoviesHttpRepoTest : TestCase(){

    private val SUT = MoviesHttpRepo()

    fun testFetchDatas() {
        //// var stringJson = SUT.fetchDatas(null) // Android Studio advise not compile
        val expected = null
        val candidatesUrl = listOf("", " ", "-", "a", "aa")
        for (candidate in candidatesUrl) {
            val stringJson = SUT.fetchMovies(candidate)
            assertEquals(expected, stringJson)
        }
    }
}