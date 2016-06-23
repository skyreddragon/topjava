package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {

    @Autowired
    private UserMealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        UserMeal meal = service.get(USER_MEAL_ID, LoggedUser.id());
        MATCHER.assertEquals(USER_MEAL, meal);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(ADMIN_MEAL_ID, LoggedUser.id());
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(USER_MEAL_ID, LoggedUser.id());
        MATCHER.assertCollectionEquals(Collections.emptyList(), service.getAll(LoggedUser.id()));
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1, LoggedUser.id());
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        UserMeal futureMeal = service.save(new UserMeal(null, LocalDateTime.of(2020, 5, 25, 11, 0), "а", 50), LoggedUser.id());
        Collection<UserMeal> singleMeal = Collections.singletonList(futureMeal);
        Collection<UserMeal> filteredMealList = service.getBetweenDateTimes(LocalDateTime.of(2019, 1, 1, 11, 0),
                LocalDateTime.of(2021, 1, 1, 21, 0), LoggedUser.id());
        MATCHER.assertCollectionEquals(singleMeal, filteredMealList);
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<UserMeal> list = service.getAll(LoggedUser.id());
        MATCHER.assertCollectionEquals(list, Collections.singleton(USER_MEAL));
    }

    @Test
    public void testUpdate() throws Exception {
        UserMeal updated = USER_MEAL;
        updated.setDescription("Другое");
        updated.setCalories(10);
        service.update(updated, LoggedUser.id());
        MATCHER.assertEquals(updated, service.get(USER_MEAL_ID, LoggedUser.id()));
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundUpdate() throws Exception {
        UserMeal updated = new UserMeal(1, LocalDateTime.MAX, "Невалидная еда", 10);
        updated.setDescription("Другое");
        updated.setCalories(10);
        service.update(updated, LoggedUser.id());
    }

    @Test(expected = NotFoundException.class)
    public void testForeignMealUpdate() throws Exception {
        UserMeal updated = ADMIN_MEAL;
        updated.setDescription("Другое");
        updated.setCalories(10);
        service.update(updated, LoggedUser.id());
    }

    @Test
    public void testSave() throws Exception {
        UserMeal meal = service.save(new UserMeal(null, LocalDateTime.now(), "Обед", 200), LoggedUser.id());
        MATCHER.assertCollectionEquals(Arrays.asList(meal, USER_MEAL), service.getAll(LoggedUser.id()));
    }
}