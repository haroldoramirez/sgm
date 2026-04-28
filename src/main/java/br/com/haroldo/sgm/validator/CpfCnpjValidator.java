package br.com.haroldo.sgm.validator;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfCnpjValidator implements ConstraintValidator<CpfCnpj, String> {

    private final CPFValidator cpfValidator = new CPFValidator();
    private final CNPJValidator cnpjValidator = new CNPJValidator();

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null || value.isBlank()) {
            return true;
        }

        String numbers = value.replaceAll("\\D", "");

        try {
            if (numbers.length() == 11) {
                cpfValidator.assertValid(numbers);
                return true;
            }

            if (numbers.length() == 14) {
                cnpjValidator.assertValid(numbers);
                return true;
            }

        } catch (Exception e) {
            return false;
        }

        return false;
    }

}