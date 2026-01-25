package com.clinicamedica.clinica_api.agenda.controller;

import com.clinicamedica.clinica_api.agenda.dto.ConsultaRequest;
import com.clinicamedica.clinica_api.agenda.dto.ConsultaResponse;
import com.clinicamedica.clinica_api.agenda.service.AgendaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AgendaController {

    private final AgendaService service;

    public AgendaController(AgendaService service) {
        this.service = service;
    }

    @PostMapping("/consultas")
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultaResponse agendar(@RequestBody @Valid ConsultaRequest request) {
        return service.agendar(request);
    }

    @GetMapping("/consultas/{id}")
    public ConsultaResponse buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/consultas")
    public List<ConsultaResponse> listar() {
        return service.listar();
    }
}