package com.ssanusi.javaoauth2.services;

import com.ssanusi.javaoauth2.exceptions.ResourceFoundException;
import com.ssanusi.javaoauth2.exceptions.ResourceNotFoundException;
import com.ssanusi.javaoauth2.logging.Loggable;
import com.ssanusi.javaoauth2.models.Role;
import com.ssanusi.javaoauth2.models.User;
import com.ssanusi.javaoauth2.models.UserRoles;
import com.ssanusi.javaoauth2.models.Useremail;
import com.ssanusi.javaoauth2.repository.RoleRepository;
import com.ssanusi.javaoauth2.repository.UserRepository;
import com.ssanusi.javaoauth2.view.UserNameCountEmails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Loggable
@Service(value = "userService")
public class UserServiceImplementation implements UserDetailsService, UserService {

    @Autowired
    UserAuditing userAuditing;

    @Autowired
    UserRepository userRepo;

    @Autowired
    RoleRepository roleRepo;


    @Override
    public List<User> findAll() {
       List<User> users =  new ArrayList<>();
       userRepo.findAll().iterator().forEachRemaining(users::add);
        return users;
    }

    @Override
    public List<User> findByNameContaining(String username) {
        return userRepo.findByUsernameContainingIgnoreCase(username.toLowerCase());
    }

    @Override
    public User findUserById(long id) {
        return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User id "+ id + " not found") );
    }

    @Override
    public User findByName(String name) {
        User user =  userRepo.findByUsername(name.toLowerCase());
        if(user == null)
            throw new ResourceNotFoundException("User name "+ name + " not found");

        return user;
    }

    @Transactional
    @Override
    public void delete(long id) {
       userRepo.findById(id).orElseThrow(() -> new  ResourceNotFoundException("User id " + id + " not found"));
       userRepo.deleteById(id);
    }

    @Transactional
    @Override
    public User save(User user) {
        if(userRepo.findByUsername(user.getUsername().toLowerCase()) != null)
            throw new ResourceFoundException(user.getUsername() + " is already Taken");

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setPrimaryemail(user.getPrimaryemail().toLowerCase());

        ArrayList<UserRoles> newRoles = new ArrayList<>();
        for(UserRoles userRoles: user.getUserroles()) {
            long id = userRoles.getRole().getRoleid();

            Role role = roleRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role id "+ id + " not found"));
            newRoles.add(new UserRoles(newUser, role));
        }

        newUser.setUserroles(newRoles);

        for (Useremail useremail: user.getUseremails())
            newUser.getUseremails().add(new Useremail(newUser, useremail.getUseremail()));

        return userRepo.save(newUser);
    }

    @Transactional
    @Override
    public User update(User user, long id) {
        User currentUser =  findUserById(id);

        if (user.getUsername() != null)
            currentUser.setUsername(user.getUsername().toLowerCase());

        if(user.getPassword() != null)
            currentUser.setPassword(user.getPassword());

        if(user.getPrimaryemail() != null)
            currentUser.setPrimaryemail(user.getPrimaryemail());

        if(user.getUserroles().size() > 0)
            throw new ResourceFoundException("User Roles are not updated through User. See endpoint POST: users/{userid}/role/{roleid}");

        if(user.getUseremails().size() > 0)
            for (Useremail useremail: user.getUseremails())
                currentUser.getUseremails().add(new Useremail(currentUser, useremail.getUseremail()));


        return userRepo.save(currentUser);
    }

    @Transactional
    @Override
    public void deleteUserRole(long userid, long roleid) {
        userRepo.findById(userid).orElseThrow(() -> new ResourceNotFoundException("User id "+ userid + " Not found"));
        roleRepo.findById(roleid).orElseThrow(() -> new ResourceNotFoundException("Role id "+ roleid + " Not found"));

        if(roleRepo.checkUserRolesCombo(userid, roleid).getCount() > 0)
            roleRepo.deleteUserRoles(userid, roleid);

        else
            throw new ResourceNotFoundException("Role and User Combination not found");

    }

    @Override
    public void addUserRole(long userid, long roleid) {
        userRepo.findById(userid).orElseThrow(() -> new ResourceNotFoundException("User id "+ userid + " Not found"));
        roleRepo.findById(roleid).orElseThrow(() -> new ResourceNotFoundException("Role id "+ roleid + " Not found"));

        if(roleRepo.checkUserRolesCombo(userid, roleid).getCount() <= 0)
            roleRepo.insertUserRoles(userAuditing.getCurrentAuditor().get(), userid, roleid);

        else
            throw new ResourceNotFoundException("Role and User Combination Already exit");


    }

    @Override
    public List<UserNameCountEmails> getCountUserEmails() {
        return userRepo.geCountUserEmails();
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if(user == null)
            throw new UsernameNotFoundException("Invalid username or password");
        return new org.springframework.security.core.userdetails.User(user.getUsername().toLowerCase(),user.getPassword(),user.getAuthority());
    }
}
