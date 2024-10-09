package com.example.marsphotos.fake

import com.example.marsphotos.data.MarsPhotosRepository
import com.example.marsphotos.model.MarsPhoto

class FakeNetworkMarsPhotosRepository : MarsPhotosRepository {
    override suspend fun getMarPhotos(): List<MarsPhoto> {
        return  FakeDataSource.photosList
    }
}