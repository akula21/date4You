package com.example.date4you.service.serviceImplementation;

import com.example.date4you.entity.Unicorn;
import com.example.date4you.repository.UnicornRepository;
import com.example.date4you.service.UnicornService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnicornServiceImplementation implements UnicornService {

    @Autowired
    private UnicornRepository unicornRepository;

    @Override
    public List<Unicorn> getUnicorns() {

        return unicornRepository.findAll();
    }

    @Override
    public void create(Unicorn unicorn) {

        unicornRepository.save(unicorn);
    }

}
