package com.ssanusi.javaoauth2.services;

import com.ssanusi.javaoauth2.logging.Loggable;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Loggable
@Component
public class UserAuditing implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {

        String uname;

        uname = "SYSTEMS";

        return Optional.of(uname);
    }
}
