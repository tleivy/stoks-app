package com.example.stoks

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object APPModule {


    @Provides
    @Singleton
    fun getAppDB(context : Application): ItemsDatabase {
        return ItemsDatabase.getDatabase(context)

    }

    @Provides
    @Singleton
    fun getDao(ItemDB: ItemsDatabase) : ItemDao {
        return ItemDB.itemsDao()
    }



}