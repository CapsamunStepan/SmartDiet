package com.progress.smartdiet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView headerTitle = findViewById(R.id.headerTitle);
        TextView imt = findViewById(R.id.imt);
        TextView status = findViewById(R.id.status);
        TextView recommendedCalories = findViewById(R.id.recommendedCalories);

        ImageView dietButton = findViewById(R.id.dietButton);
        FloatingActionButton addFoodButton = findViewById(R.id.addFoodButton);

        addFoodButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, AddFoodActivity.class);
            startActivity(intent);
        });
        

        dietButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, DietActivity.class);
            startActivity(intent);
        });

        UserProfileDatabase db = Room.databaseBuilder(getApplicationContext(),
                UserProfileDatabase.class, "user_profile_db").allowMainThreadQueries().build();
        UserProfileDao userProfileDao = db.userProfileDao();

        // Получаем профиль пользователя
        UserProfile userProfile = userProfileDao.getUserProfile();

        if (userProfile != null) {
            headerTitle.setText("Привет, " + userProfile.getName() + "!");
            imt.setText(String.valueOf(userProfile.getIndex()));
            status.setText(String.valueOf(userProfile.getStatus()) + " вес");
            recommendedCalories.setText(String.valueOf(userProfile.getRecommendedValueOfCalories()) + " ккал");
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

