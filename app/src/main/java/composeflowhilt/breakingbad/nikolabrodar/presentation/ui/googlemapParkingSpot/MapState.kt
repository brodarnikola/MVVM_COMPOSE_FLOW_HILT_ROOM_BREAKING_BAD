package composeflowhilt.breakingbad.nikolabrodar.presentation.ui.googlemapParkingSpot

import com.google.maps.android.compose.MapProperties
import composeflowhilt.breakingbad.nikolabrodar.domain.model.ParkingSpot

data class MapState(
    val properties: MapProperties = MapProperties(),
    val parkingSpots: List<ParkingSpot> = emptyList(),
    val isFalloutMap: Boolean = true
)