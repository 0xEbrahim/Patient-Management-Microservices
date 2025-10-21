package com.pm.patientservice.service;


import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.save(PatientMapper.toEntity(patientRequestDTO));
        System.out.println(patient.getId());
        return PatientMapper.toDto(patient);
    }

    public List<PatientResponseDTO> findAll() {
        List<Patient> patients = patientRepository.findAll();
        return patients
                .stream()
                .map(PatientMapper::toDto)
                .toList();
    }
}
