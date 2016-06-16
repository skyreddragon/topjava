package ru.javawebinar.topjava;

import ru.javawebinar.topjava.util.UserMealsUtil;

public class LoggedUser {
    private static int id = 1;

    public static int id() {
        return id;
    }

    public static void setId(int userId) {
        id = userId;
    }

    public static int getCaloriesPerDay() {
        return UserMealsUtil.DEFAULT_CALORIES_PER_DAY;
    }
}
