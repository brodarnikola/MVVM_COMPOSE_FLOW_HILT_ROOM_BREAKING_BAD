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
package composeflowhilt.breakingbad.nikolabrodar.presentation.ui.googlemapParkingSpot

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.MapStyleOptions
import composeflowhilt.breakingbad.nikolabrodar.domain.model.ParkingSpot
import composeflowhilt.breakingbad.nikolabrodar.domain.repository.ParkingSpotRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val repository: ParkingSpotRepository
): ViewModel() {

    var state by mutableStateOf(MapState())

    init {
        viewModelScope.launch {
            repository.getParkingSpots().collectLatest { spots ->
                state = state.copy(
                    parkingSpots = spots
                )
            }
        }
    }

    fun onEvent(event: MapEvent) {
        when(event) {
            is MapEvent.ToggleFalloutMap -> {
                state = state.copy(
                    properties = state.properties.copy(
                        mapStyleOptions = if(state.isFalloutMap) {
                            null
                        } else MapStyleOptions(MapStyle.json),
                    ),
                    isFalloutMap = !state.isFalloutMap
                )
            }
            is MapEvent.OnMapLongClick -> {
                viewModelScope.launch {
                    repository.insertParkingSpot(
                        ParkingSpot(
                        event.latLng.latitude,
                        event.latLng.longitude
                    )
                    )
                }
            }
            is MapEvent.OnInfoWindowLongClick -> {
                viewModelScope.launch {
                    repository.deleteParkingSpot(event.spot)
                }
            }
        }
    }
}