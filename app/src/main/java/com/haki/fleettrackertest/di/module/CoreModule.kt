package com.haki.fleettrackertest.di.module

import android.content.Context
import androidx.room.Room
import com.haki.fleettrackertest.core.data.source.local.LocalDataSource
import com.haki.fleettrackertest.core.data.source.local.preference.UserPreference
import com.haki.fleettrackertest.core.data.source.local.preference.dataStore
import com.haki.fleettrackertest.core.data.source.local.room.FleetDatabase
import com.haki.fleettrackertest.core.data.source.local.room.StatusLogDao

import com.haki.fleettrackertest.core.domain.repository.IFleetRepository
import com.haki.fleettrackertest.core.domain.usecase.GetAllStatusLogUseCase
import com.haki.fleettrackertest.core.domain.usecase.GetFullRouteUseCase
import com.haki.fleettrackertest.core.domain.usecase.GetLastStatusLogUseCase
import com.haki.fleettrackertest.core.domain.usecase.GetUserSessionUseCase
import com.haki.fleettrackertest.core.domain.usecase.GetVehicleMovementUseCase
import com.haki.fleettrackertest.core.domain.usecase.LoginUseCase
import com.haki.fleettrackertest.core.domain.usecase.LogoutUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : FleetDatabase {
        return Room.databaseBuilder(
            context,
            FleetDatabase::class.java,
            "fleet_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(database : FleetDatabase) : StatusLogDao {
        return database.statusLogDao()
    }

    @Provides
    @Singleton
    fun provideInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideUserPreference(@ApplicationContext context: Context): UserPreference {
        return UserPreference(context.dataStore)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(
        userPreference: UserPreference,
        dao: StatusLogDao,
    ) : LocalDataSource {
        return LocalDataSource(userPreference, dao)
    }

    @Provides
    @Singleton
    fun provideGetVehicleMovement(): GetVehicleMovementUseCase {
        return GetVehicleMovementUseCase()
    }

    @Provides
    @Singleton
    fun provideGetUserSession(
        repository: IFleetRepository
    ): GetUserSessionUseCase {
        return GetUserSessionUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLogin(
        repository: IFleetRepository
    ): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLogout(
        repository: IFleetRepository
    ): LogoutUseCase {
        return LogoutUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetLastStatusLogUseCase(
        repository: IFleetRepository
    ): GetLastStatusLogUseCase {
        return GetLastStatusLogUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllStatusLogUseCase(
        repository: IFleetRepository
    ): GetAllStatusLogUseCase {
        return GetAllStatusLogUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideGetFullRoute(
        repository: IFleetRepository
    ): GetFullRouteUseCase {
        return GetFullRouteUseCase(repository)
    }
}