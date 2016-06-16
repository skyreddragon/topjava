package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.web.to.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    private Map<Integer, Map<Integer, UserMeal>> repository = new ConcurrentHashMap<>();

    private AtomicInteger counter = new AtomicInteger(0);

    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserMealRepositoryImpl.class);

    {
        UserMealsUtil.MEAL_LIST.forEach(um -> this.save(1, um));
    }

    @Override
    public UserMeal save(int userId, UserMeal userMeal) {
        LOG.info("save " + userMeal);
        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
        }
        if (repository.get(userId) == null) {
            repository.put(userId, new HashMap<>());
        }
        repository.get(userId).put(userMeal.getId(), userMeal);
        return userMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        LOG.info("delete " + id);
        if (repository.get(userId) == null) return false;
        UserMeal meal = repository.get(userId).remove(id);
        return meal != null;
    }

    @Override
    public UserMeal get(int userId, int id) {
        LOG.info("get " + id);
        if (repository.get(userId) == null) return null;
        return repository.get(userId).get(id);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        LOG.info("getAll");
        if (repository.values().isEmpty() || repository.get(userId) == null) return Collections.emptyList();
        return repository.get(userId).values()
                .stream()
                .sorted((u1, u2) -> u2.getDateTime().compareTo(u1.getDateTime())).collect(Collectors.toList());
    }
}

