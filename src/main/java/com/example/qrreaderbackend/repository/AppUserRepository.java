package com.example.qrreaderbackend.repository;

import com.example.qrreaderbackend.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Patrik Melander
 * Date: 2021-06-16
 * Time: 13:06
 * Project: Qr-reader-backend
 * Copyright: MIT
 */
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    Optional<AppUser> findByEmail(String email);

    List<AppUser> findAllByEmail(String email);
}
