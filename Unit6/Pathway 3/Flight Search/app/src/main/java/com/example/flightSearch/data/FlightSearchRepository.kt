package com.example.flightSearch.data

import kotlinx.coroutines.flow.Flow

class FlightSearchRepository(private val airportDao: AirportDao) {
    fun getAllAirports(query: String): Flow<List<Airport>> = airportDao.getAllAirports(query)
    fun getAirportByIataCode(iataCode: String): Flow<Airport> =
        airportDao.getAirportByIataCode(iataCode)

    fun getFavouriteByIataCode(
        departureCode: String,
        destinationCode: String
    ): Flow<Favorite> =
        airportDao.getFavouriteByIataCode(departureCode, destinationCode)

    fun getAllFavorites(): Flow<List<Favorite>> = airportDao.getAllFavorites()
    fun getDestinationAirports(departingAirport: String): Flow<List<Airport>> =
        airportDao.searchDestinationAirports(departingAirport)

    suspend fun insertFavourite(favorite: Favorite) = airportDao.insertFavorite(favorite)
    suspend fun deleteFavourite(favorite: Favorite) = airportDao.deleteFavorite(favorite)

    suspend fun deleteFavouriteByIataCode(favorite: Favorite) = airportDao.deleteFavoriteByIataCode(favorite.departureCode, favorite.destinationCode)
}