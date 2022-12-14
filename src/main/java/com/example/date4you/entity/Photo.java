package com.example.date4you.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "photo")
@Access( AccessType.FIELD )
public class Photo {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @ManyToOne
    @JoinColumn( name = "profile_fk" )
    private Profile profile;

    @NotNull
//    @Pattern( regexp = "\\w{1,200}" )
    private String name;

    @Column( name = "is_profile_photo" )
    private boolean isProfilePhoto;

    //@NotNull
    private LocalDateTime created;

    public Photo() {
    }

    public Photo(Long id, Profile profile, String name, boolean isProfilePhoto, LocalDateTime created ) {
        this.id = id;
        this.profile = profile;
        this.name = name;
        this.isProfilePhoto = isProfilePhoto;
        this.created = created;
    }

    public Long getId() {
        return id;
    }

    public Profile getProfile() {
        return profile;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public boolean isProfilePhoto(boolean b) {
        return isProfilePhoto;
    }

    public void setProfilePhoto( boolean profilePhoto ) {
        isProfilePhoto = profilePhoto;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created ) {
        this.created = created;
    }

    @Override public String toString() {
        return "Photo[" + id + "]";
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public boolean isProfilePhoto() {
        return isProfilePhoto;
    }
}
