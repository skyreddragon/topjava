package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.web.to.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;


public interface UserMealService {
    void delete(int id, int userId);

    void update(UserMeal meal, int userId);

    UserMeal save(int userId, UserMeal meal);

    List<UserMealWithExceed> getAll(int userId);

    UserMeal get(int userId, int id);

    List<UserMealWithExceed> getByLocalDateTime(int id, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime);
}
