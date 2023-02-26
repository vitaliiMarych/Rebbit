package com.Rebbit.Services;

import com.Rebbit.Domain.User;
import com.Rebbit.Repo.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDetailsRepo userDetailsRepo;


    public User getCurrentUser(OAuth2User principal){
        if (principal != null) {
            Optional<User> user = userDetailsRepo.findById(principal.getAttribute("sub"));
            return user.orElse(null);
        }
        else return null;
    }
}
