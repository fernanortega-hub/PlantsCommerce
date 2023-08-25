package com.fernanortega.plantscommerce.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fernanortega.plantscommerce.data.local.model.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user_table WHERE _id = :id")
    fun getById(id: String): UserEntity?
}