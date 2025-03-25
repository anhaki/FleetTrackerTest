package com.haki.fleettrackertest.di.module

import com.haki.fleettrackertest.BuildConfig
import com.haki.fleettrackertest.core.data.source.remote.network.ApiService
import com.haki.fleettrackertest.core.domain.repository.IFleetRepository
import com.haki.fleettrackertest.core.domain.usecase.GetFullRouteUseCase
import com.haki.fleettrackertest.core.domain.usecase.GetVehicleMovementUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.MAPS_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun providesMapDirectionsService(
        retrofit: Retrofit
    ): ApiService = retrofit.create(ApiService::class.java)
    @Provides

    @Singleton
    fun provideGetVehicleMovement(): GetVehicleMovementUseCase {
        return GetVehicleMovementUseCase()
    }

    @Singleton
    fun provideGetVehicleMovement(
        repository: IFleetRepository
    ): GetFullRouteUseCase {
        return GetFullRouteUseCase(repository)
    }
}