package com.clinicamedica.clinica_api.medico.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "medicos", uniqueConstraints = {
        @UniqueConstraint(name = "uk_medicos_crm", columnNames = "crm")
})
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, length = 20)
    private String crm;

    @Column(nullable = false, length = 80)
    private String especialidade;

    protected Medico() {
    }

    public Medico(String nome, String crm, String especialidade) {
        this.nome = nome;
        this.crm = crm;
        this.especialidade = especialidade;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getCrm() { return crm; }
    public String getEspecialidade() { return especialidade; }
}