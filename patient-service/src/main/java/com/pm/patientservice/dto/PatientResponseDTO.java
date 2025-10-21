package com.pm.patientservice.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponseDTO {
    private String id;
    private String name;
    private String email;
    private String address;
    private String birthDay;
}
