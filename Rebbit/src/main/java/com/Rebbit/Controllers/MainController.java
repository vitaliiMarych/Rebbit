package com.Rebbit.Controllers;

import com.Rebbit.Domain.User;
import com.Rebbit.Repo.MessageRepo;
import com.Rebbit.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserService userService;
    @Autowired
    private MessageRepo messageRepo;

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal OAuth2User oAuth2User){
        User currentUser = userService.getCurrentUser(oAuth2User);

        HashMap<String, Object> data = new HashMap<>();

        data.put("profile", currentUser);
        data.put("messages", messageRepo.findAll());

        model.addAttribute("frontendData", data);

        return "main";
    }

}
