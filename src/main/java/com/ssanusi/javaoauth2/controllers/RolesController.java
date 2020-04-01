package com.ssanusi.javaoauth2.controllers;

import com.ssanusi.javaoauth2.handlers.RestExceptionHandler;
import com.ssanusi.javaoauth2.logging.Loggable;
import com.ssanusi.javaoauth2.models.Role;
import com.ssanusi.javaoauth2.services.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/roles", produces = {"application/json"})
@Loggable
public class RolesController {

    private RoleService roleService;

    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @GetMapping(value = "", produces = {"application/json"})
    public ResponseEntity<?> listRoles(HttpServletRequest request){
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        List<Role> allRoles = roleService.findAll();

        return new ResponseEntity<>(allRoles, HttpStatus.OK);
    }
}
