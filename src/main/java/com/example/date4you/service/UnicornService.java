package com.example.date4you.service;

import com.example.date4you.entity.Profile;
import com.example.date4you.entity.Unicorn;

import java.util.List;

public interface UnicornService {

    void create(Unicorn unicorn);

    List<Unicorn> getUnicorns();
}
