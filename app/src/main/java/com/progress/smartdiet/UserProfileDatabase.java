package com.progress.smartdiet;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {UserProfile.class}, version = 1, exportSchema = false)
public abstract class UserProfileDatabase extends RoomDatabase {
    private static volatile UserProfileDatabase INSTANCE;

    public abstract UserProfileDao userProfileDao();

    public static UserProfileDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (UserProfileDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    UserProfileDatabase.class, "user_profile_database")
                            .allowMainThreadQueries() // Использовать только для тестирования
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
