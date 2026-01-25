package com.clinicamedica.clinica_api.paciente.controller;

import com.clinicamedica.clinica_api.paciente.dto.PacienteRequest;
import com.clinicamedica.clinica_api.paciente.dto.PacienteResponse;
import com.clinicamedica.clinica_api.paciente.service.PacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PacienteResponse criar(@RequestBody @Valid PacienteRequest request) {
        return service.criar(request);
    }

    @GetMapping("/{id}")
    public PacienteResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping
    public List<PacienteResponse> listar() {
        return service.listar();
    }
}