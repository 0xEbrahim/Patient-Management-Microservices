package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> update(@RequestBody PatientRequestDTO patientRequestDTO, @PathVariable UUID id) {
        PatientResponseDTO patient = patientService.updatePatient(id, patientRequestDTO);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patient = patientService.createPatient(patientRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(patient);
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> findAll() {
        List<PatientResponseDTO> patients = patientService.findAll();
        return ResponseEntity.ok(patients);
    }
}
