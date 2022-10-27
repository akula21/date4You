package com.example.date4you.repository;

import com.example.date4you.entity.Photo;
import com.example.date4you.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    @Query("SELECT p FROM Photo p WHERE p.profile =?1")
    public List<Photo> findByProfile(Profile profile);

    @Query("SELECT p FROM Photo p WHERE p.name =?1")
    public Photo findPhotoByName(String name);

    @Query("SELECT p FROM Photo p WHERE p.profile = ?1 AND p.isProfilePhoto IS TRUE ")
    public Photo findByProfilePhoto(Profile profile);
}
