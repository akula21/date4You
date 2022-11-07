package com.example.date4you.controller;

import com.example.date4you.repository.ProfileRepository;
import com.example.date4you.security.UnicornUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @Autowired
    ProfileRepository profileRepository;

    @RequestMapping( "/"  )
    public String indexPage(Model model, Authentication auth) {

            if (auth != null) {
                UnicornUser user = (UnicornUser) auth.getPrincipal();
                model.addAttribute("name", user.getName());
                model.addAttribute("moveToUser", user.getpId());
            } else
                model.addAttribute("name", " ");


        model.addAttribute("totalProfiles", profileRepository.count());

        model.addAttribute("showSome", profileRepository.findAll().size());

        return "index";
    }



}
