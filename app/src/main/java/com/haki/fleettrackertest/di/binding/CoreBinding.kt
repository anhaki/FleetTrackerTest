package com.haki.fleettrackertest.di.binding

import com.haki.fleettrackertest.core.data.source.FleetRepository
import com.haki.fleettrackertest.core.data.source.remote.RemoteDataSource
import com.haki.fleettrackertest.core.domain.repository.IFleetRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreBinding {
    @Binds
    abstract fun bindFleetRepository(
        fleetRepository: FleetRepository
    ): IFleetRepository
}
