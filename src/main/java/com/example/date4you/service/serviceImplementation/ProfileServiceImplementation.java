package com.example.date4you.service.serviceImplementation;

import com.example.date4you.entity.Profile;
import com.example.date4you.repository.ProfileRepository;
import com.example.date4you.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileServiceImplementation implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public List<Profile> listAllAll(Byte gender, Short min, Short max) {

        if (min != null  && max != null && gender != null) {

            return profileRepository.findProfileByGenderAndHornlength(gender, min, max);
        }
        if (min != null  && max != null) {
            return profileRepository.findProfileByHornlenght(min, max);
        }

        if (gender != null) {
            return profileRepository.findProfileByGender(gender);
        }
        return profileRepository.findAll();
    }



    @Override
    public List<Profile> listAllNeededHornsAndGenders(Byte gender, Short min, Short max) {
        if (min != null  && max != null && gender != null) {
            return profileRepository.findProfileByGenderAndHornlength(gender, min, max);
        }
        return profileRepository.findAll();
    }

    @Override
    public List<Profile> listAllNeededHorns(Short min, Short max) {
        if (min != null  && max != null) {
            return profileRepository.findProfileByHornlenght(min, max);
        }
        return profileRepository.findAll();
    }

    @Override
    public List<Profile> listAllNeededGenders(Byte gender) {
        if (gender != null) {
            return profileRepository.findProfileByGender(gender);
        }
        return profileRepository.findAll();
    }

    @Override
    public List<Profile> listAllNeeded(String keyword) {
        if (keyword != null) {
            return profileRepository.findProfileByNickname(keyword);
        }
        return profileRepository.findAll();
    }

    @Override
    public void create(Profile profile)  {
        profileRepository.save(profile);
    }




}
