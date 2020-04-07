package com.ssanusi.javaoauth2.services;

import com.ssanusi.javaoauth2.logging.Loggable;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Loggable
@Component
public class UserAuditing implements AuditorAware<String> {



    @Override
    public Optional<String> getCurrentAuditor() {

        String uname;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null)
            uname = authentication.getName();
        else
            uname = "SEEDER";

        return Optional.of(uname);
    }
}
