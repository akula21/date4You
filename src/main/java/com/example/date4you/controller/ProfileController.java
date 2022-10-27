package com.example.date4you.controller;

import com.example.date4you.entity.Photo;
import com.example.date4you.entity.Profile;
import com.example.date4you.entity.Unicorn;
import com.example.date4you.repository.PhotoRepository;
import com.example.date4you.repository.ProfileRepository;
import com.example.date4you.repository.UnicornRepository;
import com.example.date4you.security.UnicornUser;
import com.example.date4you.service.ProfileFormData;
import com.example.date4you.service.ProfileService;
import com.example.date4you.service.RegisterFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    UnicornRepository unicornRepository;


    @RequestMapping(value = {"/search"}, method = RequestMethod.GET)
    public String showProfiles(Model model, Authentication auth, Photo photo, @RequestParam(value = "minAge" ,defaultValue = "18") int minAge, @RequestParam(value = "maxAge" ,defaultValue = "100")int maxAge, @Param("keyword") String keyword, @Param("gender") Byte gender, @RequestParam(value = "min", defaultValue = "0") Short min, @RequestParam(value = "max", defaultValue = "40") Short max)  {

//        List<Profile> profileList = profileService.listAllNeeded(keyword);
//        List<Profile> profileByGender = profileService.listAllNeededGenders(gender);
//          List<Profile> profileByHorns = profileService.listAllNeededHorns(min, max);
//
//        List<Profile> profiles = profileService.listAllNeededHornsAndGenders(gender, min, max);

        List<Profile> profiles = profileService.listAllAll(gender, min, max);

        //List<Photo> profilePhotos = profiles.stream().map(profile -> photoRepository.findByProfilePhoto(profile)).toList();

        List<Profile> profilesWithAge = new ArrayList<>();

        for (Profile profile : profiles) {
            if (Period.between(profile.getBirthdate(), LocalDate.now()).getYears() >= minAge &&
                    Period.between(profile.getBirthdate(), LocalDate.now()).getYears() <= maxAge) {
                profilesWithAge.add(profile);
            }
        }

        //model.addAttribute("photos", profilePhotos);

        if (auth != null) {
            UnicornUser user = (UnicornUser) auth.getPrincipal();
            model.addAttribute("name", user.getName());
            model.addAttribute("moveToUser", user.getpId());
        } else return "redirect:/index";

        model.addAttribute("profilesWithAge", profilesWithAge);

        model.addAttribute("profileAll", profiles);


//        model.addAttribute("profileAll", profiles);
//        model.addAttribute("profileByHorns", profileByHorns);
//        model.addAttribute("profileList", profileList);
//        model.addAttribute("profileByGender", profileByGender);
//        model.addAttribute("keyword", keyword);

        model.addAttribute("search", profileRepository.findAll());

        return "search";
    }


    @RequestMapping(value = { "/profile/{id}" }, method = RequestMethod.GET)
    public String showProfile(@PathVariable Long id, Model model, Authentication auth, Principal principal){



        Optional<Profile> maybeProfile = profileRepository.findById(id);

//        if (!maybeProfile.isPresent())  return "redirect:/";

        model.addAttribute("user", unicornRepository.findByEmail(principal.getName()).get().getProfile().getId());

        Profile profile = maybeProfile.get();

        model.addAttribute("uniMail", unicornRepository.findByProfile(profile).getEmail());

        model.addAttribute("profile", new ProfileFormData(
                        profile.getId(),
                        profile.getNickname(),
                        profile.getBirthdate(),
                        (short) profile.getHornlength(),
                        (short) profile.getGender(),
                        profile.getAttractedToGender(),
                        profile.getDescription(),
                        profile.getLastseen()));


        List<String> photoList = new ArrayList<>();

        if (auth != null) {
            UnicornUser user = (UnicornUser) auth.getPrincipal();
            model.addAttribute("name", user.getName());
            model.addAttribute("moveToUser", user.getpId());
        } else return "redirect:/";

        model.addAttribute("photos", profile.getPhotos());

        return "profile";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateProfile(@ModelAttribute("profile") Profile profile) {

//        Optional<Profile> profileNew = profileRepository.findById(id);

        profile.setId(profile.getId());
        profile.setNickname(profile.getNickname());
        profile.setBirthdate(profile.getBirthdate());
        profile.setHornlength(profile.getHornlength());
        profile.setGender(profile.getGender());
        profile.setAttractedToGender(profile.getAttractedToGender());
        profile.setDescription(profile.getDescription());
        profile.setLastseen(LocalDateTime.parse(LocalDateTime.now().toString()));
        profileRepository.save(profile);

        return "redirect:/profile/" + profile.getId();
    }


    @GetMapping("/register")
    public String registration(Model model, Authentication auth) {

        if (auth != null) {
            UnicornUser user = (UnicornUser) auth.getPrincipal();
            model.addAttribute("name", user.getName());
            model.addAttribute("moveToUser", user.getpId());
        }


        model.addAttribute("registerFormData", new RegisterFormData());

        return "register";
    }


    @PostMapping("/register")
    public String register(@Valid RegisterFormData regForm, Authentication auth) {

        regForm.setPassword("{noop}"+regForm.getPassword());

        Profile profile = new Profile(regForm);

        LocalDateTime time = LocalDateTime.now();
        LocalDateTime dateTime = LocalDateTime.parse(time.toString());
        profile.setLastseen(dateTime);

            Profile savedProfile = profileRepository.save(profile);

            Unicorn unicorn = new Unicorn(regForm.getEmail(), regForm.getPassword(), savedProfile);
            unicornRepository.save(unicorn);
            return "redirect:/login";
        }
    }



//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    public String SaveProfile(@ModelAttribute("unicorn") Unicorn unicorn) {
//        unicorn.setPassword("{noop}" + unicorn.getPassword());
//
//        unicornRepository.save(unicorn);
//
//        return "redirect:/create";
//    }
//
//    @RequestMapping(value = "/create", method = RequestMethod.GET)
//    public String profileCreateHere(Model model) {
//
//        model.addAttribute("createModel", new Profile());
//
//        return "create";
//
//    }
//    @RequestMapping(value = "/create", method = RequestMethod.POST)
//    public String createProfile(@ModelAttribute("createModel") Profile profile, Authentication auth, Principal principal) {
//
//
////        unicornRepository.findByEmail(auth.getName()).get().setProfile(profile);
//
//        profileRepository.save(profile);
//
//
//        LocalDateTime time = LocalDateTime.now();
//        LocalDateTime dateTime = LocalDateTime.parse(time.toString());
//        profile.setLastseen(dateTime);
//
//        profileService.create(profile);
//
//        return "redirect:/profile/" + profile.getId();
//    }
//
//    @RequestMapping(value = "/register", method = RequestMethod.GET)
//    public String registration(Model model ) {
//
//        model.addAttribute("unicorn", new Unicorn());
//
//        return "register";
//    }
