package com.example.qrreaderbackend.model;

import com.example.qrreaderbackend.DTO.AppUserResponse;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@Accessors(chain = true)
public class AppUser {

    @Id
    @GeneratedValue
    private UUID id;

    private String firstName;
    private String lastName;

    private String password;
    private String email;

    public AppUserResponse toResponse() {
        return new AppUserResponse()
                .setFirstName(this.firstName)
                .setLastName(this.lastName)
                .setEmail(this.email);
    }
}