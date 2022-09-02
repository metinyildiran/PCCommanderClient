package com.example.pccommanderclient.dependencyinjection

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.net.Socket
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Connection {

    @Singleton
    @Provides
    fun setupSocket(): Socket {
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        return Socket("192.168.1.35", 1755)
    }
}