package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.web.to.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;


public interface UserMealRepository {
    UserMeal save(int userId, UserMeal userMeal);

    // false if not found
    boolean delete(int userId, int id);

    // null if not found
    UserMeal get(int userId, int id);


    List<UserMeal> getAll(int userId);

//    List<UserMealWithExceed> geByTime(int id, LocalTime startTime, LocalTime endTime, int caloriesPerDay);
//
//    List<UserMealWithExceed> getByLocalDate(int id, LocalDate localDate, LocalDate localDate1, int defaultCaloriesPerDay);
}
