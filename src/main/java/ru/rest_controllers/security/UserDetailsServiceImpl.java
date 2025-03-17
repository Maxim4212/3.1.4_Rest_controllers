package ru.rest_controllers.security;

import ru.rest_controllers.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rest_controllers.model.User;

@Transactional
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDAO userDao;

    @Autowired
    public UserDetailsServiceImpl(UserDAO userDao) {
        this.userDao = userDao;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return user;
    }
}
