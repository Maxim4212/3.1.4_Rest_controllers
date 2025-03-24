package ru.rest_controllers.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rest_controllers.dao.RoleDAO;
import ru.rest_controllers.model.Role;
import ru.rest_controllers.model.User;
import ru.rest_controllers.service.UserService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;

@Service
public class DBInit {

    private final UserService userService;
    private final RoleDAO roleDAO;

    @Autowired
    public DBInit(UserService userService, RoleDAO roleDAO) {
        this.userService = userService;
        this.roleDAO = roleDAO;
    }

    @PostConstruct
    public void addDefaultData() {
        createDefaultRoles();

        createDefaultUsers();
    }

    private void createDefaultRoles() {
        if (!roleDAO.existsById(1L)) {
            Role roleUser = new Role("ROLE_USER");
            roleDAO.save(roleUser);
        }
        if (!roleDAO.existsById(2L)) {
            Role roleAdmin = new Role("ROLE_ADMIN");
            roleDAO.save(roleAdmin);
        }
    }

    private void createDefaultUsers() {
        if (userService.findAll().isEmpty()) {
            Set<Role> rolesUser = new HashSet<>();
            rolesUser.add(roleDAO.findById(1L).orElseThrow(() -> new EntityNotFoundException("Role USER not found")));

            Set<Role> rolesAdmin = new HashSet<>();
            rolesAdmin.add(roleDAO.findById(1L).orElseThrow(() -> new EntityNotFoundException("Role USER not found")));
            rolesAdmin.add(roleDAO.findById(2L).orElseThrow(() -> new EntityNotFoundException("Role ADMIN not found")));

            User user1 = new User("Nikolay", "Ivanov", (byte) 26, "user@mail.com", "user", "user", rolesUser);
            User user2 = new User("Ivan", "Nikolayev", (byte) 62, "admin@mail.com", "admin", "admin", rolesAdmin);

            userService.save(user1);
            userService.save(user2);
        }
    }
}
