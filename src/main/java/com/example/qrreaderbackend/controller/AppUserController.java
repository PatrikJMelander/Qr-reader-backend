package com.example.qrreaderbackend.controller;

/**
 * Created by Patrik Melander
 * Date: 2021-06-16
 * Time: 13:06
 * Project: Qr-reader-backend
 * Copyright: MIT
 */

import com.example.qrreaderbackend.DTO.AppUserResponse;
import com.example.qrreaderbackend.model.AppUser;
import com.example.qrreaderbackend.service.AppUserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@CrossOrigin
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping
    public ResponseEntity<List<AppUserResponse>> getAllAppUsers() {
        return ResponseEntity.ok(appUserService.getAll());
    }

    @GetMapping("/validate/login")
    public ResponseEntity<AppUserResponse> validateLogin(@RequestParam String email,
                                                         @RequestParam String password) {
        return ResponseEntity.ok(appUserService.validateLogin(email, password));
    }

    @GetMapping("/get/one")
    public ResponseEntity<AppUserResponse> getOneByEmail(@RequestParam  String email) {
        return ResponseEntity.ok(appUserService.getAppUserByEmail(email));
    }

    @PostMapping("/add")
    public ResponseEntity<AppUserResponse> addAppUser(@RequestBody AppUser appUser) {
        return ResponseEntity.ok(appUserService.addAppUser(appUser));
    }

    @GetMapping("/update/password")
    public ResponseEntity<AppUserResponse> updatePassword(@RequestParam String password,
                                                          @RequestParam String email) {
        return ResponseEntity.ok(appUserService.updatePassword(password,email));
    }

    @PostMapping("/update")
    public ResponseEntity<AppUserResponse> updateUser(@RequestBody AppUser appUser) {
        return ResponseEntity.ok(appUserService.updateUser(appUser));
    }

    @GetMapping("/delete")
    public ResponseEntity<String> deleteAppUser(@RequestParam String email,
                                                @RequestParam String password) {
        return ResponseEntity.ok(appUserService.deleteAppUser(email, password));
    }

    @GetMapping("/delete/bypostman")
    public ResponseEntity<String> deleteAppUserByPostman(@RequestParam String email) {
        return ResponseEntity.ok(appUserService.deleteAppUserByPostman(email));
    }

}
