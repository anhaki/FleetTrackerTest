package com.haki.fleettrackertest.core.data.source

import com.google.android.gms.maps.model.LatLng
import com.haki.fleettrackertest.core.data.source.remote.RemoteDataSource
import com.haki.fleettrackertest.core.domain.model.Route
import com.haki.fleettrackertest.core.domain.repository.IFleetRepository
import com.haki.fleettrackertest.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FleetRepository @Inject constructor() : IFleetRepository {
//    override suspend fun getDirections(origin: LatLng, destination: LatLng): Flow<Resource<Route>> = flow{
//        emit(Resource.Loading())
//        try {
//            val getRouteList = remoteDataSource.getRoutes(origin, destination)
//            val polyLinePoints = try {
//                getRouteList[0].legs[0].steps.map { step ->
//                    step.polyline.decodePolyline(step.polyline.points)
//                }
//            }catch (e: Exception){ emptyList() }
//
//            emit(Resource.Success(DataMapper.routeListToDomain(polyLinePoints)))
//        } catch (t: Throwable) {
//            emit(Resource.Error(t.message.toString()))
//        }
//    }

    override suspend fun getFullRoute(): Flow<List<LatLng>> = flow{
        emit(
            listOf(
            LatLng(-6.19231, 106.82223),
            LatLng(-6.19231, 106.82224),
            LatLng(-6.19231, 106.82237),
            LatLng(-6.19229, 106.82292),
            LatLng(-6.19093, 106.82289),
            LatLng(-6.19054, 106.82287),
            LatLng(-6.19041, 106.82286),
            LatLng(-6.18963, 106.82285),
            LatLng(-6.18931, 106.82285),
            LatLng(-6.18908, 106.82285),
            LatLng(-6.18876, 106.82287),
            LatLng(-6.18798, 106.82289),
            LatLng(-6.18786, 106.82290),
            LatLng(-6.18762, 106.82291),
            LatLng(-6.18750, 106.82292),
            LatLng(-6.18720, 106.82293),
            LatLng(-6.18703, 106.82294),
            LatLng(-6.18694, 106.82294),
            LatLng(-6.18675, 106.82295),
            LatLng(-6.18634, 106.82297),
            LatLng(-6.18607, 106.82299),
            LatLng(-6.18603, 106.82299),
            LatLng(-6.18593, 106.82300),
            LatLng(-6.18590, 106.82300),
            LatLng(-6.18583, 106.82300),
            LatLng(-6.18572, 106.82300),
            LatLng(-6.18566, 106.82300),
            LatLng(-6.18561, 106.82300),
            LatLng(-6.18554, 106.82300),
            LatLng(-6.18550, 106.82300),
            LatLng(-6.18546, 106.82299),
            LatLng(-6.18544, 106.82299),
            LatLng(-6.18541, 106.82299),
            LatLng(-6.18539, 106.82299),
            LatLng(-6.18524, 106.82298),
            LatLng(-6.18521, 106.82298),
            LatLng(-6.18511, 106.82297),
            LatLng(-6.18487, 106.82294),
            LatLng(-6.18464, 106.82293),
            LatLng(-6.18383, 106.82288),
            LatLng(-6.18353, 106.82286),
            LatLng(-6.18336, 106.82285),
            LatLng(-6.18325, 106.82285),
            LatLng(-6.18304, 106.82283),
            LatLng(-6.18281, 106.82281),
            LatLng(-6.18263, 106.82281),
            LatLng(-6.18211, 106.82279),
            LatLng(-6.18195, 106.82279),
            LatLng(-6.18172, 106.82279),
            LatLng(-6.18144, 106.82278),
            LatLng(-6.18108, 106.82278),
            LatLng(-6.18105, 106.82277),
            LatLng(-6.18102, 106.82277),
            LatLng(-6.18100, 106.82276),
            LatLng(-6.18098, 106.82276),
            LatLng(-6.18096, 106.82275),
            LatLng(-6.18089, 106.82273),
            LatLng(-6.18087, 106.82270),
            LatLng(-6.18086, 106.82268),
            LatLng(-6.18084, 106.82266),
            LatLng(-6.18082, 106.82264),
            LatLng(-6.18079, 106.82263),
            LatLng(-6.18078, 106.82262),
            LatLng(-6.18075, 106.82261),
            LatLng(-6.18072, 106.82260),
            LatLng(-6.18069, 106.82259),
            LatLng(-6.18068, 106.82259),
            LatLng(-6.18066, 106.82258),
            LatLng(-6.18063, 106.82258),
            LatLng(-6.18060, 106.82259),
            LatLng(-6.18058, 106.82259),
            LatLng(-6.18056, 106.82259),
            LatLng(-6.18055, 106.82260),
            LatLng(-6.18053, 106.82260),
            LatLng(-6.18052, 106.82261),
            LatLng(-6.18051, 106.82261),
            LatLng(-6.18051, 106.82262),
            LatLng(-6.18049, 106.82262),
            LatLng(-6.18048, 106.82263),
            LatLng(-6.18047, 106.82264),
            LatLng(-6.18042, 106.82269),
            LatLng(-6.18040, 106.82272),
            LatLng(-6.18038, 106.82275),
            LatLng(-6.18037, 106.82277),
            LatLng(-6.18036, 106.82279),
            LatLng(-6.18035, 106.82282),
            LatLng(-6.18035, 106.82286),
            LatLng(-6.18035, 106.82290),
            LatLng(-6.18036, 106.82293),
            LatLng(-6.18037, 106.82297),
            LatLng(-6.18038, 106.82301),
            LatLng(-6.18040, 106.82304),
            LatLng(-6.18042, 106.82306),
            LatLng(-6.18044, 106.82309),
            LatLng(-6.18045, 106.82309),
            LatLng(-6.18047, 106.82311),)
        )
    }
}
