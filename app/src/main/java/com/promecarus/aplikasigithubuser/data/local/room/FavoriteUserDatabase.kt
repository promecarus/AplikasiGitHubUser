package com.promecarus.aplikasigithubuser.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.promecarus.aplikasigithubuser.data.common.User

@Database(entities = [User::class], version = 1)
abstract class FavoriteUserDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao

    companion object {
        private var database: FavoriteUserDatabase? = null

        fun getDatabase(context: Context) = database ?: synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext, FavoriteUserDatabase::class.java, "favorite_user.db"
            ).build().apply {
                database = this
            }
        }
    }
}
