package org.example.moviesapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope


@Database(entities = [MovieEnty::class], version = 1)
abstract class MovieEntyRoomDatabase : RoomDatabase() {

    abstract fun entyDao(): MovieEntyDao

    companion object {
        @Volatile
        private var INSTANCE: MovieEntyRoomDatabase? = null

        fun getDatabase(
                context: Context,
                scope: CoroutineScope
        ): MovieEntyRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieEntyRoomDatabase::class.java,
                        "movie_enty_database"
                )
                        // Wipes and rebuilds instead of migrating if no Migration object.
                        .fallbackToDestructiveMigration()
//                        .addCallback(MovieEntyDatabaseCallback(scope))
                        .build()
                INSTANCE = instance
                instance
            }
        }
//
//        private class MovieEntyDatabaseCallback(
//                private val scope: CoroutineScope
//        ) : RoomDatabase.Callback() {
//
////            /**
////             * Override the onOpen method to populate the database.
////             * For this sample, we clear the database every time it is created or opened.
////             */
////            override fun onOpen(db: SupportSQLiteDatabase) {
////                super.onOpen(db)
////                // If you want to keep the data through app restarts,
////                // comment out the following line.
////                INSTANCE?.let { database ->
////                    scope.launch(Dispatchers.IO) {
//////                        populateDatabase(database.userEntyDao())
////                    }
////                }
////            }
//        }
//
////        fun populateDatabase(userEntyDao: UserEntyDao) {
////            // Start the app with a clean database every time.
////            // Not needed if you only populate on creation.
////            userEntyDao.deleteAll()
////
////            var userEnty = UserEnty("Hello")
////            userEntyDao.insert(userEnty)
////            userEnty = UserEnty("World!")
////            userEntyDao.insert(userEnty)
////        }
    }

}
