package com.example.date4you.service;

import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.Future;

public interface Thumbnail {

    @Async
    Future<byte[]> thumbnail(byte[] imageBytes );
}
