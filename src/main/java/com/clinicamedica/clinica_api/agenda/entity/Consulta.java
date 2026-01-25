package com.clinicamedica.clinica_api.agenda.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Simplificação intencional: guardamos só os IDs aqui (sem relacionamentos JPA ainda)
    @Column(name = "medico_id", nullable = false)
    private Long medicoId;

    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @Column(nullable = false)
    private LocalDateTime inicio;

    @Column(nullable = false)
    private LocalDateTime fim;

    protected Consulta() {
    }

    public Consulta(Long medicoId, Long pacienteId, LocalDateTime inicio, LocalDateTime fim) {
        this.medicoId = medicoId;
        this.pacienteId = pacienteId;
        this.inicio = inicio;
        this.fim = fim;
    }

    public Long getId() { return id; }
    public Long getMedicoId() { return medicoId; }
    public Long getPacienteId() { return pacienteId; }
    public LocalDateTime getInicio() { return inicio; }
    public LocalDateTime getFim() { return fim; }
}