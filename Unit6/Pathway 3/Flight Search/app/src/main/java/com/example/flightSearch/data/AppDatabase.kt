/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.flightSearch.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = arrayOf(Airport::class, Favorite::class), version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun airportDao(): AirportDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                val MIGRATION_1_2 = object : Migration(1, 2) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        database.execSQL(
                            """
            CREATE TABLE IF NOT EXISTS `favorite` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `departure_code` TEXT NOT NULL,
                `destination_code` TEXT NOT NULL
            )
            """
                        )
                    }
                }
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "flight_search_database"
                )
                    .createFromAsset("database/flight_search.db")
                    .addMigrations(MIGRATION_1_2)
                    .build()
                    .also { Instance = it }
            }
        }
    }

}
