package com.clinicamedica.clinica_api.agenda.repository;


import com.clinicamedica.clinica_api.agenda.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    @Query("""
            select (count(c) > 0) from Consulta c
            where c.medicoId = :medicoId
              and (:inicio < c.fim and :fim > c.inicio)
           """)
    boolean existsConflitoHorario(
            @Param("medicoId") Long medicoId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );
}