package composeflowhilt.breakingbad.nikolabrodar.presentation.ui.googlemapParkingSpot


import com.google.android.gms.maps.model.LatLng
import composeflowhilt.breakingbad.nikolabrodar.domain.model.ParkingSpot

sealed class MapEvent {
    object ToggleFalloutMap: MapEvent()
    data class OnMapLongClick(val latLng: LatLng): MapEvent()
    data class OnInfoWindowLongClick(val spot: ParkingSpot): MapEvent()
}