package com.progress.smartdiet;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class AddFoodActivity extends AppCompatActivity {

    private EditText foodNameEditText, caloriesEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        AutoCompleteTextView foodCategoryEditText = findViewById(R.id.foodCategoryEditText);

        String[] foodCategories = new String[] {"Завтрак", "Обед", "Ужин"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                foodCategories
        );

        foodCategoryEditText.setAdapter(adapter);
        foodCategoryEditText.setOnClickListener(v -> foodCategoryEditText.showDropDown());

        foodNameEditText = findViewById(R.id.foodNameEditText);
        caloriesEditText = findViewById(R.id.caloriesEditText);
        Button saveFoodButton = findViewById(R.id.saveFoodButton);

        saveFoodButton.setOnClickListener(v -> {
            String foodName = foodNameEditText.getText().toString().trim();
            String caloriesStr = caloriesEditText.getText().toString().trim();
            String category = foodCategoryEditText.getText().toString().trim();

            if (foodName.isEmpty() || caloriesStr.isEmpty() || category.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            } else {
                int calories;
                try {
                    calories = Integer.parseInt(caloriesStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Введите корректное число калорий", Toast.LENGTH_SHORT).show();
                    return;
                }

                Food food = new Food(foodName, calories, category);

                new Thread(() -> {
                    FoodDatabase db = FoodDatabase.getInstance(getApplicationContext());
                    db.foodDao().insert(food);

                    runOnUiThread(() -> {
                        Toast.makeText(this, "Еда добавлена: " + foodName + " (" + calories + " ккал)", Toast.LENGTH_LONG).show();
                        finish();
                    });
                }).start();
            }
        });

    }
}
