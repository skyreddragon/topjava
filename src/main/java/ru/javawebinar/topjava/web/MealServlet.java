package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;
    private MealService service = new MealServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("receiving GET request");
        String forward;
        int mealId;
        String paramName = request.getParameter("action");
        switch (paramName) {
            case "mealList":
                forward = "/mealList.jsp";
                request.setAttribute("meals", service.getWithExceeded(2000));
                break;
            case "delete":
                mealId = Integer.parseInt(request.getParameter("mealId"));
                service.delete(mealId);
                forward = "/mealList.jsp";
                request.setAttribute("meals", service.getWithExceeded(2000));
                break;
            case "edit":
                mealId = Integer.parseInt(request.getParameter("mealId"));
                UserMealWithExceed meal = service.read(mealId, 2000);
                forward = "/add-meal-form.jsp";
                request.setAttribute("meal", meal);
                break;
            case "return":
                response.sendRedirect("index.jsp");
                return;
            default:
                forward = "/add-meal-form.jsp";
        }
        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LOG.debug("receiving POST request");
        if (request.getParameter("id") == null) {
            LocalTime startTime = LocalTime.parse(request.getParameter("startTime"), timeFormatter);
            LocalTime endTime = LocalTime.parse(request.getParameter("endTime"), timeFormatter);
            List<UserMealWithExceed> withExceeds = service.getFilteredWithExceeded(startTime, endTime, 2000);
            request.setAttribute("meals", withExceeds);
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
            return;
        }
        String id = request.getParameter("id");
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("dateTime"), dateTimeFormatter);
        if (id == null || id.isEmpty()) {
            service.create(new UserMeal(localDateTime, description, calories));
        } else {
            UserMeal meal = new UserMeal(localDateTime, description, calories);
            meal.setId(Long.parseLong(id));
            service.update(meal);
        }
        request.setAttribute("meals", service.getWithExceeded(2000));
        request.getRequestDispatcher("/mealList.jsp").forward(request, response);
    }
}
