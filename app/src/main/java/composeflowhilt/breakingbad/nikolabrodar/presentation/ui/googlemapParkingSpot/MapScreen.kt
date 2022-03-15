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

import android.Manifest
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*
import composeflowhilt.breakingbad.nikolabrodar.presentation.utils.isPermanentlyDenied

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    viewModel: MapsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val permissionsState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                if(event == Lifecycle.Event.ON_START) {
                    permissionsState.launchPermissionRequest()
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )

    when( permissionsState.permission ) {
        Manifest.permission.ACCESS_FINE_LOCATION -> {
            when {
                permissionsState.hasPermission -> {
                    displayUIWithPermissions(viewModel)
                    // Text(text = "Camera permission accepted")
                }
                permissionsState.shouldShowRationale -> {
                    Text(text = "Location permission is needed to access the location")
                }
                permissionsState.isPermanentlyDenied() -> {
                    Text(text = "Location permission was permanently denied. You can enable it in the app settings.")
                }
            }
        }
    }

}

@Composable
fun displayUIWithPermissions(
    viewModel: MapsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    println("Map state is" + viewModel.state.isFalloutMap)

    val paris = LatLng(48.858833, 2.276995)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(paris, 10f)
    }

    val scaffoldState = rememberScaffoldState()
    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = true)
    }
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton( onClick = {
                viewModel.onEvent(MapEvent.ToggleFalloutMap)
            }) {
                Icon(
                    imageVector = if (viewModel.state.isFalloutMap) {
                        Icons.Default.ToggleOff
                    } else Icons.Default.ToggleOn,
                    contentDescription = "Toggle Fallout map"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize( fraction = 1f ),
            properties = viewModel.state.properties, // propertiesGoogleMap.component1()
            cameraPositionState = cameraPositionState,
            uiSettings = uiSettings,
            onMapLongClick = {
                viewModel.onEvent(MapEvent.OnMapLongClick(it))
            }
        ) {
            viewModel.state.parkingSpots.forEach { spot ->
                Marker(
                    position = LatLng(spot.lat, spot.lng),
                    title = "Parking spot (${spot.lat}, ${spot.lng})",
                    snippet = "Long click to delete",
                    onInfoWindowLongClick = {
                        viewModel.onEvent(
                            MapEvent.OnInfoWindowLongClick(spot)
                        )
                    },
                    onClick = {
                        it.showInfoWindow()
                        true
                    },
                    icon = BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_GREEN
                    )
                )
            }
        }
    }
}
