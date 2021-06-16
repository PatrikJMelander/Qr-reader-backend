package com.example.qrreaderbackend.service;

/**
 * Created by Patrik Melander
 * Date: 2021-06-16
 * Time: 13:06
 * Project: Qr-reader-backend
 * Copyright: MIT
 */
import com.example.qrreaderbackend.DTO.AppUserResponse;
import com.example.qrreaderbackend.model.AppUser;
import com.example.qrreaderbackend.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserService {

    private final AppUserRepository appUserRepository;


    public List<AppUserResponse> getAll() {
        return appUserRepository.findAll().stream()
                .map(AppUser::toResponse)
                .collect(Collectors.toList());
    }

    public AppUserResponse addAppUser(AppUser appUser) {
        Optional<AppUser> existingAppUser = appUserRepository.findByEmail(appUser.getEmail());

        if (existingAppUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        } else {
            validateAppUser(appUser);
            return appUserRepository.save(appUser)
                    .toResponse();
        }
    }

    @Transactional
    public AppUserResponse updateUser(AppUser appUser) {

        Optional<AppUser> existingAppUser = appUserRepository.findByEmail(appUser.getEmail());

        if (existingAppUser.isPresent()) {
            existingAppUser.get()
                    .setFirstName(appUser.getFirstName())
                    .setLastName(appUser.getLastName());
            return appUserRepository.save(existingAppUser.get())
                    .toResponse();
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Could not find user");
        }
    }

    public AppUserResponse updatePassword(String password, String email) {

        Optional<AppUser> existingAppUser = appUserRepository.findByEmail(email);

        if (existingAppUser.isPresent()) {
            existingAppUser.get()
                    .setPassword(password);
            return appUserRepository.save(existingAppUser.get())
                    .toResponse();
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Could not find user");
        }
    }

    public String deleteAppUser(String email, String password) {

        appUserRepository.findByEmail(email)
                .ifPresentOrElse(appUser -> {
                    if (appUser.getPassword().equals(password)) {
                        appUserRepository.deleteById(appUser.getId());
                    }else {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                    }
                }, () -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Email does not exist");
                });

        return "User with email: " + email + " has been deleted";
    }

    public String deleteAppUserByPostman(String email) {

        List<AppUser> usersToDelete = appUserRepository.findAllByEmail(email);

        for (AppUser user : usersToDelete) {
            appUserRepository.deleteById(user.getId());
        }

        return "Users with email: " + email + " has been deleted";
    }

    public AppUserResponse validateLogin(String email, String password) {
        AppUser appUser = appUserRepository.findByEmail(email)
                .orElseThrow(() -> { throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Could not find user");
                });
        if (appUser.getPassword().equals(password)){

            return appUser.toResponse();
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    public AppUserResponse getAppUserByEmail(String email) {

        AppUser appUser = appUserRepository.findByEmail(email)
                .orElseThrow(() -> { throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Could not find user");
                });

        return appUser.toResponse();

    }



    private void validateAppUser(AppUser appUser) {
        if (appUser.getEmail() == null || appUser.getFirstName() == null || appUser.getLastName() == null || appUser.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "User need to have a firstname, lastname, email and password");
        }
    }

}