package com.example.date4you.repository;

import com.example.date4you.entity.Profile;
import com.example.date4you.entity.Unicorn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UnicornRepository extends JpaRepository<Unicorn, Long> {

    @Query("SELECT u FROM Unicorn u WHERE u.email = ?1")
    Optional<Unicorn> findByEmail(String email);


    @Query("SELECT u FROM Unicorn u WHERE u.profile = ?1")
    public Unicorn findByProfile(Profile profile);
}
