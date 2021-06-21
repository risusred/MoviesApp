package org.example.moviesapp.repo

import org.example.moviesapp.utils.U
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MoviesHttpRepo: IRepository {

    private val tag = javaClass.simpleName
    private var httpURLConnection: HttpURLConnection? = null
    private var bufferedReader: BufferedReader? = null

    override fun fetchMovies(url: String): String? {
        le("fetchMovies  $url")
        var textJsonAsString: String? = null
        try {
            val url = URL(url)
            l("url $url")
            httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection?.requestMethod = "GET"
            httpURLConnection?.connect()
            val inputStream = httpURLConnection?.inputStream
            textJsonAsString = readInputStream(inputStream)
        } catch (e: IOException) {
            le("Error  $e")
        } finally {
            closeConnection()
        }
        return textJsonAsString
    }

    private fun readInputStream(inputStream: InputStream?): String? {
        if (inputStream == null) return null
        bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val buffer = StringBuffer()
        var line = ""
        while (bufferedReader?.readLine()?.also { line = it } != null) {
            // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
            // But it does make debugging a *lot* easier if you print out the completed
            // buffer for debugging.
            buffer.append(
                """
                    $line
                      
                """.trimIndent()
            )
        }
        if (buffer.isEmpty()) return null

        return buffer.toString()
    }

    private fun closeConnection() {
        httpURLConnection?.disconnect()
        try {
            bufferedReader?.close()
        } catch (e: IOException) {
            le("ERR $e")
        }
    }


    private fun l(msg: String){
        if(U.IS_DEBUG) le(msg)
    }

    private fun le(msg: String){
        if(U.IS_DEBUG)  U.le(tag, msg)
    }

}