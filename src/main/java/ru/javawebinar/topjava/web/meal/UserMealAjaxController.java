package ru.javawebinar.topjava.web.meal;

import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = "/ajax/profile/meals")
public class UserMealAjaxController extends AbstractUserMealController {

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public List<UserMealWithExceed> getAll() {
        return super.getAll();
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createOrUpdate(@RequestParam("id") int id,
                               @RequestParam("dateTime") LocalDateTime dateTime,
                               @RequestParam("description") String description,
                               @RequestParam("calories") int calories) {
        UserMeal meal = new UserMeal(id, dateTime, description, calories);
        if (id == 0) {
            super.create(meal);
        } else {
            super.update(meal, id);
        }
    }

    @Override
    @RequestMapping(value = "/between", method = RequestMethod.POST)
    public List<UserMealWithExceed> getBetween(
            @RequestParam("startDate") LocalDate startDate, @RequestParam("startTime") LocalTime startTime,
            @RequestParam("endDate") LocalDate endDate, @RequestParam("endTime") LocalTime endTime) {
        return super.getBetween(startDate != null ? startDate : LocalDate.MIN, startTime != null ? startTime : LocalTime.MIN,
                endDate != null ? endDate : LocalDate.MAX, endTime != null ? endTime : LocalTime.MAX);
    }
}
