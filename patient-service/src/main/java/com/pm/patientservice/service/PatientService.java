package com.pm.patientservice.service;


import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.AlreadyExistsException;
import com.pm.patientservice.exception.NotFoundException;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new AlreadyExistsException("A patient with the email: " + patientRequestDTO.getEmail() + " already exists");
        }
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

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Patient with id: " + id + " not found"));
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new AlreadyExistsException("A patient with the email: " + patientRequestDTO.getEmail() + " already exists");
        }
        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setBirthDate(LocalDate.parse(patientRequestDTO.getBirthDate()));
        Patient updated = patientRepository.save(patient);
        return  PatientMapper.toDto(updated);
    }
}
