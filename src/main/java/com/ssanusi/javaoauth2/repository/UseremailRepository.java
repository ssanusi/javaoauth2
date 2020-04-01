package com.ssanusi.javaoauth2.repository;

import com.ssanusi.javaoauth2.models.Useremail;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UseremailRepository extends CrudRepository<Useremail, Long> {
    List<Useremail> findAllByUser_Username(String name);
}
