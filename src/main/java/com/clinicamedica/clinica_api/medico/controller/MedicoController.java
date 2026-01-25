package com.clinicamedica.clinica_api.medico.controller;

import com.clinicamedica.clinica_api.medico.dto.MedicoRequest;
import com.clinicamedica.clinica_api.medico.dto.MedicoResponse;
import com.clinicamedica.clinica_api.medico.service.MedicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    private final MedicoService service;

    public MedicoController(MedicoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MedicoResponse criar(@RequestBody @Valid MedicoRequest request) {
        return service.criar(request);
    }

    @GetMapping("/{id}")
    public MedicoResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping
    public List<MedicoResponse> listar() {
        return service.listar();
    }
}