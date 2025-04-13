package com.progress.smartdiet;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Food {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public int calories;
    public String category; // например, "Завтрак", "Обед", "Ужин"

    public Food(String name, int calories, String category) {
        this.name = name;
        this.calories = calories;
        this.category = category;
    }
}

