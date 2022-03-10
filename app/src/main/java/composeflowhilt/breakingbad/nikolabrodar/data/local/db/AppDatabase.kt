/*
 * Copyright 2021 nikolabrodar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package composeflowhilt.breakingbad.nikolabrodar.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import composeflowhilt.breakingbad.nikolabrodar.data.local.db.AppDatabase.Companion.DB_VERSION
import composeflowhilt.breakingbad.nikolabrodar.data.local.model.CharacterEntity
import composeflowhilt.breakingbad.nikolabrodar.data.local.model.ParkingSpotEntity

@Database(
    entities = [
        CharacterEntity::class,
        ParkingSpotEntity::class,
    ],
    version = DB_VERSION,
    exportSchema = false
)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_VERSION = 2
        const val NAME = "app.db"
    }

    abstract fun characterDao(): CharacterDao

    abstract val parkingSpotDao: ParkingSpotDao
}
