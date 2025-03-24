package ru.rest_controllers.service;

import ru.rest_controllers.dao.UserDAO;
import ru.rest_controllers.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User passwordCoder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(long id) {
        return userDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    @Override
    @Transactional
    public void save(User user) {
        userDAO.save(passwordCoder(user));
    }

    @Override
    @Transactional
    public void update(User user) {
        User existingUser = userDAO.findById(user.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + user.getUserId()));

        if (!user.getPassword().equals(existingUser.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userDAO.save(user);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        if (!userDAO.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userDAO.deleteById(id);
    }
}

