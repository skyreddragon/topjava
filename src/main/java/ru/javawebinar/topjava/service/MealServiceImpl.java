package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MemoryMealDaoImpl;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MealServiceImpl implements MealService {

    private MealDao dao = new MemoryMealDaoImpl();

    @Override
    public UserMealWithExceed read(long id, int maxCaloriesPerDay) {
        return getWithExceeded(maxCaloriesPerDay).parallelStream().filter(um -> um.getId() == id).findFirst().get();
    }

    @Override
    public void update(UserMeal meal) {
        dao.update(meal);
    }

    @Override
    public void delete(long id) {
        dao.delete(id);
    }

    @Override
    public List<UserMealWithExceed> getWithExceeded(int maxCaloriesPerDay) {
        Map<Long, UserMeal> mealMap = dao.getAllMapped();
        return UserMealsUtil.getExceeded(mealMap, maxCaloriesPerDay);
    }

    @Override
    public List<UserMealWithExceed> getFilteredWithExceeded(LocalTime startTime, LocalTime endTime, int maxCaloriesPerDay) {
        Collection<UserMeal> userMeals = dao.getAll();
        return UserMealsUtil.getFilteredWithExceeded(new ArrayList<>(userMeals), startTime, endTime, maxCaloriesPerDay);
    }

    @Override
    public void create(UserMeal meal) {
        dao.create(meal);
    }
}
