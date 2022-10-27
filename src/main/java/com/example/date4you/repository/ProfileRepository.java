package com.example.date4you.repository;

import com.example.date4you.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("SELECT p FROM Profile p WHERE p.gender = ?1")
    List<Profile> findProfileByGender(byte gender);

    @Query("SELECT p FROM Profile p WHERE p.hornlength BETWEEN ?1 AND ?2")
    List<Profile> findProfileByHornlenght(short min, short max);

    @Query( value = "SELECT p FROM Profile WHERE hornlength > ?1",
            nativeQuery = true )
    List<Profile> findProfilesWithHornlengthGreaterThan( short minHornlength );

    @Query("SELECT p FROM Profile p WHERE p.gender=?1 AND p.hornlength BETWEEN ?2 AND ?3")
    List<Profile> findProfileByGenderAndHornlength(byte gender, short min, short max);

    @Query("SELECT p FROM Profile p WHERE p.nickname LIKE %?1% ")
    List<Profile> findProfileByNickname(String name);



}
