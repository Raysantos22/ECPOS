package com.example.possystembw.DAO

import androidx.room.*
import com.example.possystembw.database.Discount
import kotlinx.coroutines.flow.Flow

@Dao
interface DiscountDao {
    @Query("SELECT * FROM discounts")
    suspend fun getAllDiscounts(): List<Discount>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(discounts: List<Discount>)
}