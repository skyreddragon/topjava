package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.time.Month;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;


/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {
    public static final int USER_MEAL_ID = START_SEQ + 2;
    public static final int ADMIN_MEAL_ID = START_SEQ + 3;

    public static final UserMeal USER_MEAL = new UserMeal(USER_MEAL_ID, LocalDateTime.of(2014, Month.JANUARY, 25, 11, 0), "Ужин", 4000);
    public static final UserMeal ADMIN_MEAL = new UserMeal(ADMIN_MEAL_ID, LocalDateTime.of(2015, Month.MAY, 21, 13, 0), "Завтрак", 2000);

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);
}


