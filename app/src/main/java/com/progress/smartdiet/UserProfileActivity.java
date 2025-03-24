package com.progress.smartdiet;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import java.util.Calendar;

public class UserProfileActivity extends AppCompatActivity {
    private EditText editTextName, editTextBirthDate, editTextHeight, editTextWeight;
    private Spinner spinnerGoal, spinnerActivityLevel, spinnerGender;
    private UserProfileDao userProfileDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Инициализация базы данных
        UserProfileDatabase db = Room.databaseBuilder(getApplicationContext(),
                UserProfileDatabase.class, "user_profile_db").allowMainThreadQueries().build();
        userProfileDao = db.userProfileDao();

        editTextName = findViewById(R.id.editTextName);
        editTextBirthDate = findViewById(R.id.editTextBirthDate);
        editTextBirthDate.setFocusable(false);
        editTextBirthDate.setOnClickListener(v -> {
            editTextBirthDate.setInputType(InputType.TYPE_NULL);
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year1, month1, dayOfMonth) -> {
                        @SuppressLint("DefaultLocale")
                        String date = String.format("%02d.%02d.%04d", dayOfMonth, month1 + 1, year1);
                        editTextBirthDate.setText(date);
                    }, year, month, day);
            datePickerDialog.show();
        });

        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        spinnerGoal = findViewById(R.id.spinnerGoal);
        spinnerActivityLevel = findViewById(R.id.spinnerActivityLevel);
        spinnerGender = findViewById(R.id.spinnerGender);
        Button buttonSave = findViewById(R.id.buttonSave);

        // Настройка адаптеров
        spinnerGender.setAdapter(createAdapter(new String[]{"Мужской", "Женский"}));
        spinnerGoal.setAdapter(createAdapter(new String[]{"Похудеть", "Поддерживать форму", "Набрать мышечную массу"}));
        spinnerActivityLevel.setAdapter(createAdapter(new String[] {
                "Сидячий образ жизни",
                "Прогулки",
                "Фитнес 3-5 раз в неделю",
                "Тяжелый физический труд",
                "Подготовка к соревнованиям"}));

        buttonSave.setOnClickListener(v -> saveUserProfile());
    }

    private ArrayAdapter<String> createAdapter(String[] items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private void saveUserProfile() {
        String name = editTextName.getText().toString();
        String birthDate = editTextBirthDate.getText().toString();
        String heightStr = editTextHeight.getText().toString();
        String weightStr = editTextWeight.getText().toString();
        String gender = spinnerGender.getSelectedItem().toString();
        String goal = spinnerGoal.getSelectedItem().toString();
        String activityLevelStr = spinnerActivityLevel.getSelectedItem().toString();

        if (name.isEmpty() || birthDate.isEmpty() || heightStr.isEmpty() || weightStr.isEmpty()) {
            Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        int height = Integer.parseInt(heightStr);
        float weight = Float.parseFloat(weightStr);
        float activityLevel = getActivityLevelValue(activityLevelStr);

        UserProfile userProfile = new UserProfile(name, birthDate, height, weight, goal, activityLevel, 0, 0);
        userProfile.setGender(gender);
        int age = userProfile.calculateAge();
        userProfile.setAge(age);

        // Получаем результаты расчетов ИМТ и рекомендованных калорий
        HealthCalculator.IMTResult imtResult = HealthCalculator.calculateIMT(userProfile);

        // Устанавливаем значения ИМТ и рекомендованных калорий
        userProfile.setIndex(imtResult.getImtValue());
        userProfile.setRecommendedValueOfCalories(imtResult.getRecommendedCalories());
        userProfile.setStatus(imtResult.getStatus());

        // Проверяем, существует ли уже профиль в базе данных
        UserProfile existingProfile = userProfileDao.getUserProfile(); // Предполагается, что метод getUserProfile() возвращает единственный профиль
        if (existingProfile != null) {
            // Обновляем существующий профиль
            userProfile.setId(existingProfile.getId()); // Сохраняем старый ID для обновления
            userProfileDao.update(userProfile); // Обновляем профиль в базе данных
            Toast.makeText(this, "Профиль обновлен!", Toast.LENGTH_SHORT).show();
        } else {
            // Добавляем новый профиль
            userProfileDao.insert(userProfile);
            Toast.makeText(this, "Данные сохранены!", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(UserProfileActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private float getActivityLevelValue(String activityLevel) {
        switch (activityLevel) {
            case "Прогулки": return 1.3f;
            case "Фитнес 3-5 раз в неделю": return 1.5f;
            case "Тяжелый физический труд": return 1.7f;
            case "Подготовка к соревнованиям": return 1.9f;
            default: return 1.2f;
        }
    }
}
