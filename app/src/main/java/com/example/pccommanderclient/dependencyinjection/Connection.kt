package com.example.pccommanderclient.dependencyinjection

import android.os.StrictMode
import com.example.pccommanderclient.service.CommandAPI
import com.example.pccommanderclient.util.SentResult
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.Socket
import java.net.UnknownHostException
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Connection {

    @Singleton
    @Provides
    fun setupSocket(): SentResult<Socket> {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        return try {
            val socket = Socket("192.168.1.35", 1755)

            SentResult.Success(socket)
        } catch (uhe: UnknownHostException) {
            SentResult.Error(uhe.message!!)
        } catch (io: IOException) {
            SentResult.Error(io.message!!)
        }
    }

    @Singleton
    @Provides
    fun setupRetrofit(): CommandAPI {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.35:1755")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CommandAPI::class.java)
    }
}