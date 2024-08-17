package com.example.taskappnavarro.model.provider

import android.content.Context
import androidx.room.Room
import com.example.taskappnavarro.model.room.DataDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TaskProvider {
    fun provideRetrofit(): Retrofit {
        val endpointUrl= "https://curso-android-55-1.vercel.app/"
        return Retrofit.Builder()
            .baseUrl(endpointUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun provideRoom(context: Context): DataDatabase {
        return Room.databaseBuilder(context, DataDatabase::class.java, "Data-db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
}