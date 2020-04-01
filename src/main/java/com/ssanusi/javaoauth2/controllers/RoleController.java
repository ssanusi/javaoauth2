package com.ssanusi.javaoauth2.controllers;

import com.ssanusi.javaoauth2.handlers.RestExceptionHandler;
import com.ssanusi.javaoauth2.logging.Loggable;
import com.ssanusi.javaoauth2.models.Role;
import com.ssanusi.javaoauth2.services.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "/roles", produces = {"application/json"})
@Loggable
public class RoleController {

    @Autowired
    private RoleService roleService;

    public static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @GetMapping(value = "", produces = {"application/json"})
    public ResponseEntity<?> listRoles(HttpServletRequest request) {
        logRequest(request);

        List<Role> allRoles = roleService.findAll();

        return new ResponseEntity<>(allRoles, HttpStatus.OK);
    }

    @GetMapping(value = "/{roleId}", produces = {"application/json"})
    public ResponseEntity<?> getRoleById(HttpServletRequest request, @PathVariable Long roleId) {
        logRequest(request);

        Role role = roleService.findRoleById(roleId);

        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    private void logRequest(HttpServletRequest request) {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
    }

    @GetMapping(value = "/name/{roleName}", produces = {"appliaction/json"})
    public ResponseEntity<?> getRoleByName(HttpServletRequest request, @PathVariable String roleName) {

        logRequest(request);

        Role role = roleService.findByName(roleName);

        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PostMapping(value = "/role", consumes = {"application/json"})
    public ResponseEntity<?> addRole(HttpServletRequest request, @Valid @RequestBody Role newRole) throws URISyntaxException {

        logRequest(request);
        newRole = roleService.save(newRole);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newRoleURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{roleid}").buildAndExpand(newRole.getRoleid()).toUri();

        responseHeaders.setLocation(newRoleURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{roleId}", produces = {"application/json"})
    public ResponseEntity<?> updateRole(HttpServletRequest request, @Valid @RequestBody Role newRole, @PathVariable long roleId) {

        logRequest(request);

        newRole = roleService.update(roleId, newRole);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{roleId}")
    public ResponseEntity<?> deleteRoleById(HttpServletRequest request, @PathVariable long roleId) {

        logRequest(request);

        roleService.delete(roleId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
