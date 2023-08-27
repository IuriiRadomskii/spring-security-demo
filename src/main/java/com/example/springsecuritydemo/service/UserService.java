package com.example.springsecuritydemo.service;

import com.example.springsecuritydemo.dto.UserProfileDto;
import com.example.springsecuritydemo.exceptions.AppUserNotFoundException;
import com.example.springsecuritydemo.model.entity.UserProfile;
import com.example.springsecuritydemo.model.repository.UserProfileRepository;
import com.example.springsecuritydemo.security.exception.AlreadyExistsException;
import com.example.springsecuritydemo.security.model.repository.AppUserRepository;
import com.example.springsecuritydemo.service.mappers.UserProfileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final AppUserRepository appUserRepository;
    private final UserProfileRepository userProfileRepository;

    public UserProfileDto addUserProfile(UserProfileDto userProfileDto) {
        var login = getCurrentUserLogin();
        var appUser = appUserRepository.findByLogin(login)
                .orElseThrow(() -> new AppUserNotFoundException("User not found"));
        var profile = userProfileRepository.findProfileByLogin(login);
        if (profile.isPresent()) {
            throw new AlreadyExistsException("User profile already exists");
        }
        var userProfile = UserProfile
            .builder()
            .appUser(appUser)
            .name(userProfileDto.getName())
            .surname(userProfileDto.getSurname())
            .birthTown(userProfileDto.getBirthTown())
            .birthDate(userProfileDto.getBirthDate())
            .about(userProfileDto.getAbout())
            .build();
        userProfile = userProfileRepository.save(userProfile);
        return UserProfileMapper.INSTANCE.toDto(userProfile);
    }

    public UserProfileDto updateUserProfile(UserProfileDto userProfileDto) {
        var userProfile = getCurrentUserProfile();
        UserProfileMapper.INSTANCE.updateEntity(userProfileDto, userProfile);
        userProfile = userProfileRepository.save(userProfile);
        return UserProfileMapper.INSTANCE.toDto(userProfile);
    }

    public UserProfileDto getUserProfile() {
        var userProfile = getCurrentUserProfile();
        return UserProfileMapper.INSTANCE.toDto(userProfile);
    }

    private UserProfile getCurrentUserProfile() {
        return userProfileRepository.findProfileByLogin(getCurrentUserLogin()).orElseThrow(
                () -> new AppUserNotFoundException("User profile not found"));
    }

    private String getCurrentUserLogin() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
