package com.Rebbit.Configurations;

import com.Rebbit.Domain.User;
import com.Rebbit.Repo.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final UserDetailsRepo userDetailsRepo;

    public CustomAuthenticationSuccessHandler(UserDetailsRepo userDetailsRepo){
        this.userDetailsRepo = userDetailsRepo;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");

        String id = (String) attributes.get("sub");
        String name = (String) attributes.get("name");
        String local = (String) attributes.get("locale");
        String userPic = (String) attributes.get("picture");
        LocalDateTime lastVisit = LocalDateTime.now();

        User user = userDetailsRepo.findById(id).orElse(null);

        if (user == null) {
            user = new User(id,name,userPic,email,local,lastVisit);
        } else {
            user.setName(name);
            user.setUserPic(userPic);
            user.setLastVisit(lastVisit);
        }

        userDetailsRepo.save(user);
        response.sendRedirect("/");
    }
}
