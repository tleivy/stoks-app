package com.example.stoks.data.repository

import com.example.stoks.data.remote_db.StockRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockDataRepository @Inject constructor(
    private val remoteDataSource: StockRemoteDataSource,
    private val localDataSource: StockRemoteDataSource
) {
}