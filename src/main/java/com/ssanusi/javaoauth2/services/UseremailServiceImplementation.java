package com.ssanusi.javaoauth2.services;

import com.ssanusi.javaoauth2.exceptions.ResourceNotFoundException;
import com.ssanusi.javaoauth2.logging.Loggable;
import com.ssanusi.javaoauth2.models.Useremail;
import com.ssanusi.javaoauth2.repository.UseremailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Loggable
@Service(value = "useremailService")
public class UseremailServiceImplementation implements UseremailService {

    @Autowired
    private UseremailRepository useremailRepo;
    private Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


    @Override
    public List<Useremail> findAll() {
        List<Useremail> useremails = new ArrayList<>();
        useremailRepo.findAll().iterator().forEachRemaining(useremails::add);
        return useremails;
    }

    @Override
    public Useremail findUseremailById(long id) {
        return useremailRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Useremail with id " + id + " Not found"));
    }

    @Override
    public List<Useremail> findByUserName(String username, boolean isAdmin) {
        if(username.equalsIgnoreCase(authentication.getName().toLowerCase()) || isAdmin)
            return useremailRepo.findAllByUser_Username(username.toLowerCase());
        else
            throw new ResourceNotFoundException(authentication.getName() + " not Authorized to make change");

    }

    @Override
    public void delete(long id, boolean isAdmin) {
        if(useremailRepo.findById(id).isPresent()){
            if(useremailRepo.findById(id).get().getUser().getUsername().equalsIgnoreCase(authentication.getName()) || isAdmin)
                useremailRepo.deleteById(id);
            else
                throw new ResourceNotFoundException(authentication.getName() + " not Authorized to make change");
        }

        else
            throw new ResourceNotFoundException("Useremail with id " + id + " Not Found!");
    }


    @Override
    public Useremail update(long useremailid, String emailaddress, boolean isAdmin) {
        if (useremailRepo.findById(useremailid).isPresent()) {
            if (useremailRepo.findById(useremailid).get().getUser().getUsername().equalsIgnoreCase(authentication.getName()) || isAdmin) {

                Useremail useremail = findUseremailById(useremailid);
                useremail.setUseremail(emailaddress.toLowerCase());
                return useremailRepo.save(useremail);
            } else {
                throw new ResourceNotFoundException(authentication.getName() + " not authorized to make changes");
            }
        } else {
            throw new ResourceNotFoundException("Useremail with id " + useremailid + " Not Found!");
        }
    }
}
