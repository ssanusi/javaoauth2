package com.ssanusi.javaoauth2.controllers;

import com.ssanusi.javaoauth2.handlers.RestExceptionHandler;
import com.ssanusi.javaoauth2.logging.Loggable;
import com.ssanusi.javaoauth2.models.User;
import com.ssanusi.javaoauth2.models.Useremail;
import com.ssanusi.javaoauth2.services.UseremailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Loggable
@RestController
@RequestMapping(value = "/useremails")
public class UseremailController {

    @Autowired
    private UseremailService useremailService;

    public static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    private void logRequest(HttpServletRequest request) {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "", produces = {"application/json"})
    public ResponseEntity<?> getAllUseremail(HttpServletRequest request){
        logRequest(request);
        List<Useremail> useremails = new ArrayList<>();
        useremailService.findAll().iterator().forEachRemaining(useremails::add);
        return new ResponseEntity<>(useremails, HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<?> getUserEmails(HttpServletRequest request, @PathVariable long id){
        logRequest(request);
        Useremail useremail = useremailService.findUseremailById(id);

        return new ResponseEntity<>(useremail, HttpStatus.OK);
    }


    @GetMapping(value = "/username/{username}", produces = {"application/json"})
    public ResponseEntity<?> getUserEmailByUsername(HttpServletRequest request, @PathVariable String username){
        logRequest(request);

        List<Useremail> useremail = useremailService.findByUserName(username, request.isUserInRole("ADMIN"));
        return new ResponseEntity<>(useremail, HttpStatus.OK);
    }


    @PutMapping(value = "/{useremailid}/email/{emailaddress}")
    public ResponseEntity<?> updateUserEmail(HttpServletRequest request, @PathVariable long useremailid, @PathVariable String emailaddress){
        logRequest(request);

        useremailService.update(useremailid, emailaddress, request.isUserInRole("ADMIN"));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{useremailid}")
    public ResponseEntity<?> deleteUserEmailById(HttpServletRequest request, @PathVariable long useremailid){
        logRequest(request);
        useremailService.delete(useremailid, request.isUserInRole("ADMIN"));
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
