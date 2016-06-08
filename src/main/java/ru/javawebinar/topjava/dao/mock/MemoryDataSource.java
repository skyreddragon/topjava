package ru.javawebinar.topjava.dao.mock;

import ru.javawebinar.topjava.model.UserMeal;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryDataSource {
    private final Map<Long, UserMeal> mealList = new ConcurrentSkipListMap<>();
    private AtomicLong counter = new AtomicLong(mealList.size());

    private MemoryDataSource() {

    }

    private static class MemoryDataSourceWrapper {
        static final MemoryDataSource INSTANCE = new MemoryDataSource();
    }

    public static MemoryDataSource getInstance() {
        return MemoryDataSourceWrapper.INSTANCE;
    }

    public Map<Long, UserMeal> getAllMeals() {
        return mealList;
    }

    public void createMeal(UserMeal meal) {
        meal.setId(counter.incrementAndGet());
        mealList.put(meal.getId(), meal);
    }

    public UserMeal readMeal(long id) {
        return mealList.get(id);
    }

    public void deleteMeal(long id) {
        mealList.remove(id);
    }

    public void updateMeal(UserMeal meal) {
        UserMeal readMeal = readMeal(meal.getId());
        readMeal.setCalories(meal.getCalories());
        readMeal.setDescription(meal.getDescription());
        readMeal.setDateTime(meal.getDateTime());
    }
}
