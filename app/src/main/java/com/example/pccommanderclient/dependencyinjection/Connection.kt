package com.example.pccommanderclient.dependencyinjection

import com.example.pccommanderclient.service.RequestAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Connection {

    @Singleton
    @Provides
    fun setupRetrofit(): RequestAPI {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.39:1755")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RequestAPI::class.java)
    }
}