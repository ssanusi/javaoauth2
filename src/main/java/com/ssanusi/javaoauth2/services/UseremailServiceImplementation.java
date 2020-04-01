package com.ssanusi.javaoauth2.services;

import com.ssanusi.javaoauth2.exceptions.ResourceNotFoundException;
import com.ssanusi.javaoauth2.logging.Loggable;
import com.ssanusi.javaoauth2.models.Useremail;
import com.ssanusi.javaoauth2.repository.UseremailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Loggable
@Service(value = "useremailService")
public class UseremailServiceImplementation implements UseremailService {

    @Autowired
    private UseremailRepository useremailRepo;


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
    public List<Useremail> findByUserName(String username) {
        return useremailRepo.findAllByUser_Username(username.toLowerCase());
    }

    @Override
    public void delete(long id) {
        if(useremailRepo.findById(id).isPresent())
            useremailRepo.deleteById(id);

        else
            throw new ResourceNotFoundException("Useremail with id " + id + " Not Found!");
    }

    @Override
    public Useremail update(long useremailid, String emailaddress) {
        if(useremailRepo.findById(useremailid).isPresent()){
            Useremail useremail = findUseremailById(useremailid);
            useremail.setUseremail(emailaddress.toLowerCase());
            return useremailRepo.save(useremail);
        }else {
            throw new ResourceNotFoundException("Useremail with id " + useremailid + " Not Found!");
        }
    }
}
