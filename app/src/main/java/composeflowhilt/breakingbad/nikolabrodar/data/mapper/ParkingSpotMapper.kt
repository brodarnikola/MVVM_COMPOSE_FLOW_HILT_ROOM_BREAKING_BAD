package composeflowhilt.breakingbad.nikolabrodar.data.mapper

import composeflowhilt.breakingbad.nikolabrodar.data.local.model.ParkingSpotEntity
import composeflowhilt.breakingbad.nikolabrodar.domain.model.ParkingSpot


fun ParkingSpotEntity.toParkingSpot(): ParkingSpot {
    return ParkingSpot(
        lat = lat,
        lng = lng,
        id = id
    )
}

fun ParkingSpot.toParkingSpotEntity(): ParkingSpotEntity {
    return ParkingSpotEntity(
        lat = lat,
        lng = lng,
        id = id
    )
}