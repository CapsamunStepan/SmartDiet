package com.progress.smartdiet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            UserProfileDatabase db = Room.databaseBuilder(getApplicationContext(),
                    UserProfileDatabase.class, "user_profile_db").allowMainThreadQueries().build();
            UserProfileDao userProfileDao = db.userProfileDao();
            UserProfile userProfile = userProfileDao.getUserProfile();

            Intent intent;
            if (userProfile != null) {
                // Если профиль есть, переходим в HomeActivity
                intent = new Intent(MainActivity.this, HomeActivity.class);
            } else {
                // Если профиль отсутствует, переходим в WelcomeActivity
                intent = new Intent(MainActivity.this, WelcomeActivity.class);
            }

            startActivity(intent);
            finish(); // Закрываем текущую активность
        }, 2000); // 2000 мс = 2 секунды
    }
}
