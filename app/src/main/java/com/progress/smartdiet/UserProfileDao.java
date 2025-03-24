package com.progress.smartdiet;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserProfileDao {

    // Вставка нового профиля (заменяет старый, если он уже есть)
    @Insert
    void insert(UserProfile userProfile);

    // Обновление существующего профиля
    @Update
    void update(UserProfile userProfile);

    // Получение профиля (так как профиль один, берем первую запись)
    @Query("SELECT * FROM user_profiles LIMIT 1")
    UserProfile getUserProfile();

    // Удаление всех профилей (чтобы не было нескольких записей)
    @Query("DELETE FROM user_profiles")
    void deleteAll();
}
