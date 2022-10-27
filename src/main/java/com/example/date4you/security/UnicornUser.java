package com.example.date4you.security;

import com.example.date4you.entity.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UnicornUser extends User {
    Long pId;
    String name;

    Profile profile;

    public UnicornUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Long id,
                       String name) {
        super(username, password, authorities);
        this.pId = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getpId() {
        return pId;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
