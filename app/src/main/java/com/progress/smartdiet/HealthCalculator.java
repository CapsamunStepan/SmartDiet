package com.progress.smartdiet;

import java.util.Objects;

public class HealthCalculator {

    public static class IMTResult {
        private final float imtValue;
        private final String status;
        private final int recommendedCalories;

        public IMTResult(float imtValue, String status, int recommendedCalories) {
            this.imtValue = imtValue;
            this.status = status;
            this.recommendedCalories = recommendedCalories;
        }

        public float getImtValue() {
            return imtValue;
        }

        public String getStatus() {
            return status;
        }

        public int getRecommendedCalories() {
            return recommendedCalories;
        }
    }

    public static IMTResult calculateIMT(UserProfile profile) {
        if (profile == null) {
            throw new IllegalArgumentException("UserProfile не может быть null");
        }

        float weight = profile.getWeight();
        float height = profile.getHeight() / 100.0f; // переводим в метры
        float imtValue = weight / (height * height);
        imtValue = Math.round(imtValue * 10.0f) / 10.0f; // округляем до 1 знака после запятой

        String status;
        if (imtValue < 18.5) {
            status = "Недостаточный";
        } else if (imtValue < 25) {
            status = "Нормальный";
        } else if (imtValue < 30) {
            status = "Избыточный";
        } else {
            status = "Ожирение";
        }

        // Расчет калорий
        int age = profile.getAge();
        String gender = profile.getGender(); // 1 - мужчина, 0 - женщина
        float activityLevel = profile.getActivityLevel();

        float formula = 9.99f * weight + 6.25f * profile.getHeight() - 4.92f * age;
        if (Objects.equals(gender, "Мужской")) {
            formula += 5; // Мужчины
        } else {
            formula -= 161; // Женщины
        }

        formula *= activityLevel;
        int recommendedCalories = Math.round(formula);

        // Корректировка по статусу ИМТ
        if (status.equals("Недостаточный")) {
            recommendedCalories = Math.round(recommendedCalories * 1.2f);
        } else if (status.equals("Избыточный") || status.equals("Ожирение")) {
            recommendedCalories = Math.round(recommendedCalories * 0.8f);
        } else {
            if (profile.getScope().equals("weight_loss")) {
                recommendedCalories = Math.round(recommendedCalories * 0.8f);
            } else if (profile.getScope().equals("muscle_gain")) {
                recommendedCalories = Math.round(recommendedCalories * 1.2f);
            }
        }

        return new IMTResult(imtValue, status, recommendedCalories);
    }
}
