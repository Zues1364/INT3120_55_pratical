package com.example.flightSearch.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {
    @Query("SELECT * FROM airport WHERE name LIKE '%' || :query || '%' OR iata_code LIKE '%' || :query || '%'")
    fun getAllAirports(query: String): Flow<List<Airport>>

    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): Flow<List<Favorite>>

    @Query("SELECT * FROM airport WHERE iata_code = :iataCode")
    fun getAirportByIataCode(iataCode: String): Flow<Airport>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query(
        "SELECT * FROM favorite WHERE departure_code LIKE :departureCode" +
                " AND destination_code LIKE :destinationCode"
    )
    fun getFavouriteByIataCode(departureCode: String, destinationCode: String): Flow<Favorite>

    @Query("SELECT * FROM airport WHERE name NOT LIKE :departingAirport")
    fun searchDestinationAirports(departingAirport: String): Flow<List<Airport>>

    @Query("DELETE FROM favorite WHERE departure_code LIKE :departureCode AND destination_code LIKE :destinationCode")
    suspend fun deleteFavoriteByIataCode(departureCode: String, destinationCode: String)
}


