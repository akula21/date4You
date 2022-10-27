package com.example.date4you.service;

import com.example.date4you.entity.Photo;
import com.example.date4you.entity.Profile;
import com.example.date4you.repository.PhotoRepository;
import com.example.date4you.repository.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.io.UncheckedIOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
@Validated
public class PhotoService {

    private final Logger log = LoggerFactory.getLogger( getClass() );

    private final FileSystem fs;
    private final Thumbnail thumbnail;

    private final ProfileRepository profileRepository;
    // private final ApplicationEventPublisher publisher;

    public PhotoService( FileSystem fs, Thumbnail thumbnail, ProfileRepository profileRepository ) {
        this.fs = fs;
        this.thumbnail = thumbnail;
        this.profileRepository = profileRepository;
    }

    @Cacheable( "date4You.filesystem.file" )
    public Optional<byte[]> download(String name ) {
        try {
            return Optional.of( fs.load( name + ".jpg" ) );
        }
        catch ( UncheckedIOException e ) {
            return Optional.empty();
        }
    }

    public Optional<String> savePhoto(long id, byte[] data) {
        Optional<Profile> prof = profileRepository.findById(Long.valueOf(id));
        if (prof.isPresent()) {
            Profile profile = prof.get();
            String name = this.upload(data);
            Photo p = new Photo(null, profile, name, false, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            profile.add(p);
            profileRepository.saveAndFlush(profile);
            return Optional.of(name);
        } else {
            return Optional.empty();
        }
    }

    @Cacheable( cacheNames = "date4You.filesystem.file", key = "#photo.name" )
    public Optional<byte[]> download( @Valid Photo photo ) {
        return download( photo.getName() );
    }

    public String upload( byte[] imageBytes ) {
        Future<byte[]> thumbnailBytes = thumbnail.thumbnail( imageBytes );

        String imageName = UUID.randomUUID().toString();

        //    NewPhotoEvent newPhotoEvent = new NewPhotoEvent( imageName, OffsetDateTime.now() );
        //    publisher.publishEvent( newPhotoEvent );

        // First: store original image
        fs.store( imageName + ".jpg", imageBytes );

        // Second: store thumbnail
        try {
            log.info( "upload" );
            fs.store( imageName + "-thumb.jpg", thumbnailBytes.get() );
        }
        catch (InterruptedException | ExecutionException e ) {
            throw new IllegalStateException( e );
        }

        return imageName;
    }
}
