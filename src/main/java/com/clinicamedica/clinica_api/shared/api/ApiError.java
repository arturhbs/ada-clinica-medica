package com.clinicamedica.clinica_api.shared.api;
// Quando algo der errado, você quer retornar um JSON consistente, por exemplo: 
// {
//   "timestamp": "...",
//   "status": 400,
//   "error": "Bad Request",
//   "message": "CPF já cadastrado",
//   "path": "/pacientes"
// }
import java.time.Instant;

public record ApiError(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path
) {
}