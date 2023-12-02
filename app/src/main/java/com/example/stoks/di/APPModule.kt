package com.example.stoks.di

import android.content.Context
import com.example.stoks.data.local.StocksDatabase
import com.example.stoks.data.remote_db.StockRemoteService
import com.example.stoks.data.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object APPModule {

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideStockService(retrofit: Retrofit): StockRemoteService =
        retrofit.create(StockRemoteService::class.java)

    @Provides
    fun provideLocalDatabase(@ApplicationContext appContext: Context): StocksDatabase =
        StocksDatabase.getDatabase(appContext)

    @Provides
    @Singleton
    fun provideItemDao(database: StocksDatabase) = database.itemsDao()

}