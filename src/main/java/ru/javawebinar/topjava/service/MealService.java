package ru.javawebinar.topjava.service;


import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalTime;
import java.util.List;

public interface MealService {
    UserMealWithExceed read(long id,int maxCaloriesPerDay);

    void update(UserMeal meal);

    void delete(long id);

    List<UserMealWithExceed> getWithExceeded(int maxCaloriesPerDay);

    List<UserMealWithExceed> getFilteredWithExceeded(LocalTime startTime, LocalTime endTime, int maxCaloriesPerDay);

    void create(UserMeal meal);
}
