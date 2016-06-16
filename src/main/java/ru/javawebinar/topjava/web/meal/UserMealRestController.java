package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.web.to.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class UserMealRestController {
    private static final Logger LOG = LoggerFactory.getLogger(UserMealRestController.class);
    @Autowired
    private UserMealService service;

    public void delete(int id) {
        LOG.info("delete" + id);
        service.delete(id, LoggedUser.id());
    }

    public void update(UserMeal meal) {
        LOG.info("update" + meal);
        service.update(meal, LoggedUser.id());
    }

    public UserMeal save(UserMeal meal) {
        LOG.info("save" + meal);
        return service.save(LoggedUser.id(), meal);
    }

    public List<UserMealWithExceed> getAll() {
        LOG.info("getAll");
        return service.getAll(LoggedUser.id());
    }

    public UserMeal get(int id) {
        LOG.info("get" + id);
        return service.get(LoggedUser.id(), id);
    }

    public List<UserMealWithExceed> getByLocalDateTime(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        LOG.info("getByLocalDateTime");
        if (startDate != null && endDate != null && startTime != null && endTime != null) {
            return service.getByLocalDateTime(LoggedUser.id(), startDate, endDate, startTime, endTime);
        } else if (startDate != null && endDate != null) {
            return service.getByLocalDateTime(LoggedUser.id(), startDate, endDate, LocalTime.MIN, LocalTime.MAX);
        } else if (startTime != null && endTime != null) {
            return service.getByLocalDateTime(LoggedUser.id(), LocalDate.MIN, LocalDate.MAX, startTime, endTime);
        }
        return getAll();
    }
}
