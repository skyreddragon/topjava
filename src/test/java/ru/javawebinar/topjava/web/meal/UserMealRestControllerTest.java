package ru.javawebinar.topjava.web.meal;


import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.web.meal.UserMealRestController.REST_URL;

public class UserMealRestControllerTest extends AbstractControllerTest {

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + "/100005"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(userMealService.get(100005, AuthorizedUser.id())));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + "/" + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER_WITH_EXCEEDED.assertCollectionEquals(UserMealsUtil.getWithExceeded(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2),
                AuthorizedUser.getCaloriesPerDay()),
                UserMealsUtil.getWithExceeded(userMealService.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay()));
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_WITH_EXCEEDED.contentListMatcher(UserMealsUtil.getWithExceeded(userMealService.getAll(AuthorizedUser.id()),
                        AuthorizedUser.getCaloriesPerDay()))));
    }


    @Test
    public void testUpdate() throws Exception {
        UserMeal updated = userMealService.get(MEAL1_ID, AuthorizedUser.id());
        updated.setDescription("обновленное описание");
        updated.setCalories(10);
        mockMvc.perform(put(REST_URL + "/" + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, userMealService.get(MEAL1_ID, AuthorizedUser.id()));
    }

    @Test
    public void testCreate() throws Exception {
        UserMeal expected = new UserMeal(LocalDateTime.now(), "новая еда", 25);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        UserMeal returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());
        MATCHER.assertEquals(expected, returned);
        MATCHER.assertCollectionEquals(Arrays.asList(expected, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), userMealService.getAll(AuthorizedUser.id()));
    }

    @Test
    public void testBetween() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + "/filter?startTime=2012-12-03T10:55:30&endTime=2016-12-03T21:00:30"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_WITH_EXCEEDED.contentListMatcher(UserMealsUtil
                        .getFilteredWithExceeded(userMealService.getBetweenDates(LocalDate.of(2012, 12, 3),
                                LocalDate.of(2016, 12, 3), AuthorizedUser.id()),
                                LocalTime.of(10, 55, 30), LocalTime.of(21, 0, 30),
                                AuthorizedUser.getCaloriesPerDay()))));
    }
}