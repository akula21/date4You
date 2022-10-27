package com.example.date4you.service;

import com.example.date4you.entity.Profile;

import java.util.List;

public interface ProfileService {


    List<Profile> listAllAll(Byte gender, Short min, Short max);

    List<Profile> listAllNeededHornsAndGenders(Byte gender, Short min, Short max);

    List<Profile> listAllNeededHorns(Short min, Short max);

    List<Profile> listAllNeededGenders(Byte gender);

    List<Profile> listAllNeeded(String keyword);

    void create(Profile profile);


}
