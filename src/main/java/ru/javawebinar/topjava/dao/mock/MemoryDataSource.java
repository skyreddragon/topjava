package ru.javawebinar.topjava.dao.mock;

import ru.javawebinar.topjava.model.UserMeal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class MemoryDataSource {
    private final Map<Long, UserMeal> mealList = new ConcurrentSkipListMap<Long, UserMeal>() {
        {
            put(1L, new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 12), "Завтрак", 500));
            put(2L, new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 12, 15), "Обед", 500));
            put(3L, new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 18, 21), "Ужин", 1501));
            put(4L, new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 12), "Завтрак", 500));
            put(5L, new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 18, 21), "Ужин", 500));
            put(6L, new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 12), "Завтрак", 500));
        }
    };

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
