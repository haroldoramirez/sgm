package br.com.haroldo.sgm.validator.cpfcnpj;

public class CpfCnpjValidar {

    public static boolean validar(String cpfCnpj) {

        if (cpfCnpj == null || cpfCnpj.isBlank()) {
            return false;
        }

        String numbers = cpfCnpj.replaceAll("\\D", "");

        if (todosCaracteresIguais(numbers)) {
            return false;
        }

        if (numbers.length() == 11) {
            return Cpf.validar(numbers);
        }

        if (numbers.length() == 14) {
            return Cnpj.validar(numbers);
        }

        return false;

    }

    public static boolean todosCaracteresIguais(String valor) {

        if (valor == null || valor.isEmpty()) {
            return false;
        }

        char primeiro = valor.charAt(0);

        for (int i = 1; i < valor.length(); i++) {
            if (valor.charAt(i) != primeiro) {
                return false;
            }
        }

        return true;

    }

}