package com.example.reactdemoback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {

    private String name;
    private String surname;
    private String birthTown;
    private String birthDate;
    private String about;

}
