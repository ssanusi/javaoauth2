package com.ssanusi.javaoauth2.services;

import com.ssanusi.javaoauth2.exceptions.ResourceNotFoundException;
import com.ssanusi.javaoauth2.exceptions.ResourceFoundException;
import com.ssanusi.javaoauth2.logging.Loggable;
import com.ssanusi.javaoauth2.models.Role;
import com.ssanusi.javaoauth2.repository.RoleRepository;
import com.ssanusi.javaoauth2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Loggable
@Service(value = "roleService")
public class RoleServiceImplementation implements RoleService {

    @Autowired
    RoleRepository rolerepo;

    @Autowired
    UserRepository userrepo;

    @Override
    public List<Role> findAll() {
        List<Role> roleList = new ArrayList<>();
        rolerepo.findAll().iterator().forEachRemaining(roleList::add);
        return roleList;
    }

    @Override
    public Role findRoleById(long id) {
        return rolerepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role id " + id + " not found!"));
    }

    @Override
    public Role findByName(String name) {
        Role userRole = rolerepo.findByNameIgnoreCase(name);
        if(userRole != null)
            return userRole;
        else
            throw new ResourceNotFoundException(name);
    }


    @Transactional
    @Override
    public void delete(long id) {
        rolerepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role id " + id + " not Found"));
        rolerepo.deleteById(id);
    }

    @Transactional
    @Override
    public Role save(Role role) {
        Role newRole  = new Role();
        newRole.setName(role.getName());

        if(role.getUserRolesList().size() > 0)
            throw new ResourceFoundException("User Roles are not updated through Role. See endpoint POST: users/user/{userid}/role/{roleid}");

        return rolerepo.save(role);
    }


    @Transactional
    @Override
    public Role update(long id, Role role) {
        if(role.getName() == null)
            throw new ResourceNotFoundException("No role name found to update");

        if(role.getUserRolesList().size() > 0)
            throw new ResourceFoundException("User Roles are not updated through Role. See endpoint POST: users/user/{userid}/role/{roleid}");

        Role newRole = findRoleById(id);

        rolerepo.updateRoleName(id, role.getName());

        return findRoleById(id);
    }

}
