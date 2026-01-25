package com.clinicamedica.clinica_api.medico.repository;

import com.clinicamedica.clinica_api.medico.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    boolean existsByCrm(String crm);
}