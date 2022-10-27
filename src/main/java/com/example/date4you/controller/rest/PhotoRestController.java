package com.example.date4you.controller.rest;

import com.example.date4you.service.PhotoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@RestController
public class PhotoRestController {

    private final PhotoService photos;

    public PhotoRestController(PhotoService photos) {
        this.photos = photos;
    }

    @GetMapping(path = "/api/photos/{imagename}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] photo(@PathVariable String imagename) {
        return photos.download(imagename).orElseThrow();
    }

}
