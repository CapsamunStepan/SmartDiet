package com.progress.smartdiet;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class ViewProfileActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        TextView headerTitle = findViewById(R.id.headerTitle);


        UserProfileDatabase db = Room.databaseBuilder(getApplicationContext(),
                UserProfileDatabase.class, "user_profile_db").allowMainThreadQueries().build();
        UserProfileDao userProfileDao = db.userProfileDao();

        // Получаем профиль пользователя
        UserProfile userProfile = userProfileDao.getUserProfile();

        if (userProfile != null) {
            headerTitle.setText("Ваш профиль");
        } else {
            Toast.makeText(this, "Профиль не найден", Toast.LENGTH_SHORT).show();
        }
    }

    // Преобразуем уровень активности в строковое представление
    private String getActivityLevelString(float activityLevel) {
        if (activityLevel == 1.2f) return "Сидячий образ жизни";
        if (activityLevel == 1.3f) return "Прогулки";
        if (activityLevel == 1.5f) return "Фитнес 3-5 раз в неделю";
        if (activityLevel == 1.7f) return "Тяжелый физический труд";
        if (activityLevel == 1.9f) return "Подготовка к соревнованиям";
        return "Неизвестно";
    }


}

