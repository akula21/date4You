package com.example.date4you.controller;


import com.example.date4you.entity.Photo;
import com.example.date4you.entity.Profile;
import com.example.date4you.repository.PhotoRepository;
import com.example.date4you.repository.ProfileRepository;
import com.example.date4you.repository.UnicornRepository;
import com.example.date4you.security.UnicornUser;
import com.example.date4you.service.FileSystem;
import com.example.date4you.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
public class PhotoController {

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    UnicornRepository unicornRepository;

    @Autowired
    PhotoService photos;

    public static String UPLOAD_DIRECTORY = "src/main/resources/static/img";

    @GetMapping("/uploadimage")
    public String displayUploadForm() {
        return "imageupload/index";
    }

    @PostMapping("/upload")
    public String uploadImage(Authentication auth,Model model, @RequestParam("image") MultipartFile file) throws IOException {
        UnicornUser unicornUser = (UnicornUser) auth.getPrincipal();
        String filename = UUID.randomUUID().toString();

        photos.savePhoto(unicornUser.getpId(),file.getBytes());

//        Photo photo = new Photo();

//        StringBuilder fileNames = new StringBuilder();
//        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
//        fileNames.append(file.getOriginalFilename());
//        Files.write(fileNameAndPath, file.getBytes());
//
//        Profile profile = unicornRepository.findByEmail("duane.devane@brekke.org").get().getProfile();
//
//        photo.setName(file.getOriginalFilename());
//        photo.setProfile(profile);
//        photo.setProfilePhoto(false);
//
//        LocalDateTime time = LocalDateTime.now();
//        LocalDateTime dateTime = LocalDateTime.parse(time.toString());
//        photo.setCreated(dateTime);
//
//        photoRepository.save(photo);

        model.addAttribute("msg", "Uploaded images: " + filename);

        return "redirect:/profile/" +   unicornUser.getpId();
    }


    @GetMapping(path = "/images/{name}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> photoByName(@PathVariable("name") String name) {

        return ResponseEntity.of(photos.download(name));
    }

//    @GetMapping(path = "/photos{id}", produces = MediaType.IMAGE_JPEG_VALUE)
//    public byte[] get(@PathVariable("id") String id) {
//        return photos.
//    }
}
