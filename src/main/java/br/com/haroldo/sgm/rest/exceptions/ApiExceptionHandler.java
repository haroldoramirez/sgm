package br.com.haroldo.sgm.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDatabaseError(Exception ex) {

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("status", 400);
        resposta.put("mensagem", "CPF/CNPJ já cadastrado");

        return ResponseEntity.badRequest().body(resposta);

    }

    // Erros de validacao (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, String> erros = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            erros.put(error.getField(), error.getDefaultMessage());
        }

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("status", HttpStatus.BAD_REQUEST.value());
        resposta.put("erros", erros);

        return ResponseEntity.badRequest().body(resposta);

    }

    // Erro generico
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericError(Exception ex) {

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        resposta.put("mensagem", "Erro interno: " + ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);

    }

}