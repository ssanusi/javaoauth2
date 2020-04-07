package com.ssanusi.javaoauth2.services;

import com.ssanusi.javaoauth2.models.User;
import com.ssanusi.javaoauth2.view.UserNameCountEmails;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    UserDetails loadUserByUsername(String username);

    List<User> findAll();

    List<User> findByNameContaining(String username);

    User findUserById(long id);

    User findByName(String name);

    void delete(long id);

    User save(User user);

    User update(User user, long id, boolean isAdmin);

    void deleteUserRole(long userid, long roleid);

    void addUserRole(long userid, long roleid);

}
