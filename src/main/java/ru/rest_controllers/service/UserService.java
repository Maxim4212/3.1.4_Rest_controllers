package ru.rest_controllers.service;

import ru.rest_controllers.model.User;
import java.util.List;

public interface UserService {

    List<User> findAll ();
    User getById(long id);
    void save(User user);
    void deleteById(long id);
    void update(User user);
    User passwordCoder(User user);
}
