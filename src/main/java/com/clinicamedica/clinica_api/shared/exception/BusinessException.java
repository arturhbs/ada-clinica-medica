package com.clinicamedica.clinica_api.shared.exception;
// Quando a regfra de negócio for violada, exempl0: "CRM já cadastrado", "Horário indisponível", "Agendamento no passado"
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}