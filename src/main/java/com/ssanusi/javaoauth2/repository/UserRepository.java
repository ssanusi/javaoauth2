package com.ssanusi.javaoauth2.repository;

import com.ssanusi.javaoauth2.models.User;
import com.ssanusi.javaoauth2.view.UserNameCountEmails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

    List<User> findByUsernameContainingIgnoreCase(String name);

}
