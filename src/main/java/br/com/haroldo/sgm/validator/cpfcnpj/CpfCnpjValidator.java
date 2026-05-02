package br.com.haroldo.sgm.validator.cpfcnpj;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfCnpjValidator implements ConstraintValidator<CpfCnpj, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null || value.isBlank()) {
            return true;
        }

        boolean valido = CpfCnpjValidar.validar(value);

        if (!valido) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("CPF/CNPJ inválido").addConstraintViolation();
        }

        return valido;

    }

}