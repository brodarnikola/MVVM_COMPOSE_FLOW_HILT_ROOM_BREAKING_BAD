package composeflowhilt.breakingbad.nikolabrodar.data.repository

import composeflowhilt.breakingbad.nikolabrodar.data.local.db.ParkingSpotDao
import composeflowhilt.breakingbad.nikolabrodar.data.mapper.toParkingSpot
import composeflowhilt.breakingbad.nikolabrodar.data.mapper.toParkingSpotEntity
import composeflowhilt.breakingbad.nikolabrodar.domain.model.ParkingSpot
import composeflowhilt.breakingbad.nikolabrodar.domain.repository.ParkingSpotRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ParkingSpotRepositoryImpl(
    private val dao: ParkingSpotDao
): ParkingSpotRepository {

    override suspend fun insertParkingSpot(spot: ParkingSpot) {
        dao.insertParkingSpot(spot.toParkingSpotEntity())
    }

    override suspend fun deleteParkingSpot(spot: ParkingSpot) {
        dao.deleteParkingSpot(spot.toParkingSpotEntity())
    }

    override fun getParkingSpots(): Flow<List<ParkingSpot>> {
        return dao.getParkingSpots().map { spots ->
            spots.map { it.toParkingSpot() }
        }
    }
}