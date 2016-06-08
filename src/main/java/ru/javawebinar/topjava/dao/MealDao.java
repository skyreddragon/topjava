package ru.javawebinar.topjava.dao;


import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.util.Collection;
import java.util.Map;

public interface MealDao {
    void create(UserMeal meal);

    UserMeal read(long id);

    void update(UserMeal meal);

    void delete(long id);

    Map<Long, UserMeal> getAllMapped();

    Collection<UserMeal> getAll();

}
