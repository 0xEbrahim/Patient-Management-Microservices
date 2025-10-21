package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import com.pm.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient", description = "A documentation for the patient API")
public class PatientController {
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PutMapping("/{id}")
    @Operation(description = "Update a patient")
    public ResponseEntity<PatientResponseDTO> update(@Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO, @PathVariable UUID id) {
        PatientResponseDTO patient = patientService.updatePatient(id, patientRequestDTO);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @Operation(description = "Create a patient")
    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patient = patientService.createPatient(patientRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(patient);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete a patient")
    public ResponseEntity<?> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Operation(description = "Get All patients")
    public ResponseEntity<List<PatientResponseDTO>> findAll() {
        List<PatientResponseDTO> patients = patientService.findAll();
        return ResponseEntity.ok(patients);
    }
}
