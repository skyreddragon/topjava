package ru.javawebinar.topjava.dao;


import ru.javawebinar.topjava.dao.mock.MemoryDataSource;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MemoryMealDaoImpl implements MealDao {

    private MemoryDataSource memoryDataSource = MemoryDataSource.getInstance();

    @Override
    public void create(UserMeal meal) {
        memoryDataSource.createMeal(meal);
    }

    @Override
    public UserMeal read(long id) {
        return memoryDataSource.readMeal(id);
    }

    @Override
    public void update(UserMeal meal) {
        memoryDataSource.updateMeal(meal);
    }

    @Override
    public void delete(long id) {
        memoryDataSource.deleteMeal(id);
    }

    @Override
    public Map<Long, UserMeal> getAllMapped() {
        return memoryDataSource.getAllMeals();
    }

    public Collection<UserMeal> getAll() {
        return memoryDataSource.getAllMeals().values();
    }
}
