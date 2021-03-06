package com.ssanusi.javaoauth2.controllers;

import com.ssanusi.javaoauth2.handlers.RestExceptionHandler;
import com.ssanusi.javaoauth2.logging.Loggable;
import com.ssanusi.javaoauth2.models.User;
import com.ssanusi.javaoauth2.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Loggable
@RestController
@RequestMapping(value = "/users", produces = {"application/json"})
public class UserController {

    @Autowired
    UserService userService;

    public static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    private void logRequest(HttpServletRequest request) {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "", produces = {"application/json"})
    public ResponseEntity<?> getAllUsers(HttpServletRequest request) {

        logRequest(request);

        List<User> users = new ArrayList<>();
        userService.findAll().iterator().forEachRemaining(users::add);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/{userId}", produces = {"application/json"})
    public ResponseEntity<?> getuserByid(HttpServletRequest request, @PathVariable long userId) {

        logRequest(request);
        User user = userService.findUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/name/like/{username}", produces = {"application/json"})
    public ResponseEntity<?> getNameLike(HttpServletRequest request, @PathVariable String username) {

        logRequest(request);
        List<User> userList = userService.findByNameContaining(username);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }


    @GetMapping(value = "/user/name/{usrname}", produces = {"application/json"})
    public ResponseEntity<?> getUserByName(HttpServletRequest request, @PathVariable String username){

        logRequest(request);
        User user = userService.findByName(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/getuserinfo", produces = {"application/json"})
    public ResponseEntity<?> getCurrentUserInfo(HttpServletRequest request, Authentication authorization){
        logRequest(request);
        User user = userService.findByName(authorization.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/getusername", produces = {"application/json"})
    public ResponseEntity<?> getCurrentUserName(HttpServletRequest request, Authentication authorization){

        logRequest(request);
        return new ResponseEntity<>(authorization.getPrincipal(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "", consumes = {"application/json"})
    public ResponseEntity<?> addNewUser(HttpServletRequest request, @Valid @RequestBody User newUser){
        logRequest(request);

        newUser = userService.save(newUser);

        HttpHeaders responseHeader = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/userid").buildAndExpand(newUser.getUserid()).toUri();
        responseHeader.setLocation(newUserURI);

        return new ResponseEntity<>(null, responseHeader, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/{userId}/role/{roleId}")
    public ResponseEntity<?> addUserRoleById(HttpServletRequest request, @PathVariable long userId, @PathVariable long roleId){
        logRequest(request);
        userService.addUserRole(userId, roleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{userId}", consumes = {"application/json"})
    public ResponseEntity<?> updateUser(HttpServletRequest request, @Valid @RequestBody User updateUser, @PathVariable long userId){

        logRequest(request);
        userService.update(updateUser,userId, request.isUserInRole("ADMIN"));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping(value = "/{userId}/role/{roleId}")
    public ResponseEntity<?> deleteUserById(HttpServletRequest request, @PathVariable long userId, @PathVariable long roleId){
        logRequest(request);
        userService.deleteUserRole(userId, roleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
