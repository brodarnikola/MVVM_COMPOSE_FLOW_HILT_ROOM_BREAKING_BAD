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
package composeflowhilt.breakingbad.nikolabrodar.app.inject

import composeflowhilt.breakingbad.nikolabrodar.data.local.db.AppDatabase
import composeflowhilt.breakingbad.nikolabrodar.data.repository.ParkingSpotRepositoryImpl
import composeflowhilt.breakingbad.nikolabrodar.domain.repository.ParkingSpotRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ParkingSpotRepositoryModule {

    @Provides
    @Singleton
    fun provideParkingSpotRepository(appDatabase: AppDatabase): ParkingSpotRepository {
        return ParkingSpotRepositoryImpl(appDatabase.parkingSpotDao())
    }
}
