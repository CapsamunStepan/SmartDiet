package com.progress.smartdiet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DietActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);

        TextView headerTitle = findViewById(R.id.headerTitle);
        headerTitle.setText("Твоя еда на сегодня");

    }
}

