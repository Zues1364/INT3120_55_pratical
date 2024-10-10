package com.example.amphibians.data

import com.example.amphibians.model.Amphibian
import com.example.amphibians.networks.AmphibiansApiService

interface AmphibiansRepository {
    suspend fun getAmphibians(): List<Amphibian>
}
class NetworkAmphibiansRepository(private val amphibiansApiService: AmphibiansApiService) :
    AmphibiansRepository {
    override suspend fun getAmphibians(): List<Amphibian>  = amphibiansApiService.getAmphibians()
}