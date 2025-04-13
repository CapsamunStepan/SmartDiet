package com.progress.smartdiet;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FoodDao {

    @Insert
    void insert(Food food);

    @Query("SELECT * FROM Food")
    List<Food> getAll();

    @Query("SELECT * FROM Food WHERE category = :category")
    List<Food> getByCategory(String category);
}


