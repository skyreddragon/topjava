package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Inherited;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
@RequestMapping(value = "/meals")
public class UserMealController extends AbstractUserMealController {

    @RequestMapping(params = "action=create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("mealEdit");
        modelAndView.addObject("meal", new UserMeal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000));
        return modelAndView;
    }

    @RequestMapping(params = "action=update", method = RequestMethod.GET)
    public ModelAndView update(@RequestParam int id) {
        ModelAndView modelAndView = new ModelAndView("mealEdit");
        modelAndView.addObject("meal", super.get(id));
        return modelAndView;
    }

    @RequestMapping(params = "action=delete", method = RequestMethod.GET)
    public ModelAndView deleteFromModel(@RequestParam int id) {
        ModelAndView modelAndView = new ModelAndView("redirect:meals");
        super.delete(id);
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllUserMeals() {
        ModelAndView modelAndView = new ModelAndView("mealList");
        modelAndView.addObject("mealList", super.getAll());
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView createOrUpdateModel(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("redirect:meals");
        final UserMeal meal = new UserMeal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories"))
        );
        if (request.getParameter("id").isEmpty()) {
            super.create(meal);
        } else {
            super.update(meal, getId(request));
        }
        return modelAndView;
    }

    @RequestMapping(params = "action=filter", method = RequestMethod.POST)
    public ModelAndView getBetween(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("mealList");
        LocalDate startDate = TimeUtil.parseLocalDate(resetParam("startDate", request));
        LocalDate endDate = TimeUtil.parseLocalDate(resetParam("endDate", request));
        LocalTime startTime = TimeUtil.parseLocalTime(resetParam("startTime", request));
        LocalTime endTime = TimeUtil.parseLocalTime(resetParam("endTime", request));
        modelAndView.addObject("mealList", super.getBetween(startDate, startTime, endDate, endTime));
        return modelAndView;
    }

    private String resetParam(String param, HttpServletRequest request) {
        String value = request.getParameter(param);
        request.setAttribute(param, value);
        return value;
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}