package com.ssanusi.javaoauth2.services;

import com.ssanusi.javaoauth2.models.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    void delete(long id);

    Role save(Role role);

    Role findByName(String name);

    Role update(long id, Role role);

    Role findRoleById(long id);

}
