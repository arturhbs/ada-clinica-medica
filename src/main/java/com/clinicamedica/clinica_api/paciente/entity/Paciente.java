package com.clinicamedica.clinica_api.paciente.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pacientes", uniqueConstraints = {
    @UniqueConstraint(name = "uk_pacientes_cpf", columnNames = "cpf")
})

public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, length = 11)
    private String cpf;

    @Column(length = 20)
    private String telefone;

    @Column(length = 120)
    private String email;

    protected Paciente(){
    }

    public Paciente(String nome, String cpf, String telefone, String email){
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    public Long getId() {return id;}
    public String getNome() {return nome;}
    public String getCpf() {return cpf;}
    public String getTelefone() {return telefone;}
    public String getEmail() {return email;}
    
    public void atualizar(String nome, String telefone, String email){
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }
}
