package com.progress.smartdiet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome); // Указываем макет, где есть кнопка

        // Находим кнопку по ID
        Button buttonGetStarted = findViewById(R.id.buttonGetStarted);

        // Устанавливаем обработчик нажатия
        buttonGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переход на UserProfileActivity
                Intent intent = new Intent(WelcomeActivity.this, UserProfileActivity.class);
                startActivity(intent);
                finish(); // (по желанию) Закрыть WelcomeActivity, чтобы не вернуться назад
            }
        });
    }
}
