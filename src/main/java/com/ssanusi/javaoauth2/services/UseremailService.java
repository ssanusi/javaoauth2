package com.ssanusi.javaoauth2.services;

import com.ssanusi.javaoauth2.models.Useremail;

import java.util.List;

public interface UseremailService {

    List<Useremail> findAll();

    Useremail findUseremailById(long id);

    List<Useremail> findByUserName(String username);

    void delete(long id);

    Useremail update(long useremailid, String emailaddress);
}
