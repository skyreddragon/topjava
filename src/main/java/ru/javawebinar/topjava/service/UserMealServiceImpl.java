package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.web.to.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class UserMealServiceImpl implements UserMealService {
    private static final Logger LOG = LoggerFactory.getLogger(UserMealServiceImpl.class);
    @Autowired
    private UserMealRepository repository;

    @Override
    public void delete(int id, int userId) {
        LOG.info("delete UserMeal{} User{}", id, userId);
        ExceptionUtil.checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @Override
    public void update(UserMeal meal, int userId) {
        LOG.info("update UserMeal{} User{}", meal.getId(), userId);
        ExceptionUtil.checkNotFoundWithId(repository.save(userId, meal), meal.getId());
    }

    @Override
    public UserMeal save(int userId, UserMeal meal) {
        LOG.info("save UserMeal{} User{}", meal.getId(), userId);
        return repository.save(userId, meal);
    }

    @Override
    public List<UserMealWithExceed> getAll(int userId) {
        LOG.info("getAll User{}", userId);
        return UserMealsUtil.getWithExceeded(repository.getAll(userId), UserMealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    @Override
    public UserMeal get(int userId, int id) {
        LOG.info("get UserMeal{} User{}", id, userId);
        return repository.get(userId, id);
    }

    @Override
    public List<UserMealWithExceed> getByLocalDateTime(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        LOG.info("getByLocalDateTime User{}", userId);
        List<UserMeal> meals = repository.getAll(userId).stream().filter(ex -> TimeUtil.isBetween(ex.getDateTime().toLocalDate(), startDate, endDate))
                .collect(Collectors.toList());
        return UserMealsUtil.getFilteredWithExceeded(meals, startTime, endTime, UserMealsUtil.DEFAULT_CALORIES_PER_DAY);
    }
}
