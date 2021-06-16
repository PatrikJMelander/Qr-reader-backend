package com.example.qrreaderbackend.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by Patrik Melander
 * Date: 2021-06-16
 * Time: 13:06
 * Project: Qr-reader-backend
 * Copyright: MIT
 */
@Getter
@Setter
@Accessors(chain = true)
public class AppUserResponse {

    private String firstName;

    private String lastName;

    private String password;

    private String email;
}
