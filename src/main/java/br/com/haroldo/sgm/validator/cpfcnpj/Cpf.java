package br.com.haroldo.sgm.validator.cpfcnpj;

public class Cpf {

    public static boolean validar(String cpf) {

        // Verifica se o CPF tem 11 digitos
        if (cpf.length() != 11) {
            return false;
        }

        // Calcula o primeiro digito verificador
        int soma = 0;

        for (int i = 0; i < 9; i++) {
            soma += (10 - i) * (cpf.charAt(i) - '0');
        }

        int primeiroDigito = 11 - (soma % 11);
        if (primeiroDigito > 9) {
            primeiroDigito = 0;
        }

        // Verifica o primeiro digito verificador
        if ((cpf.charAt(9) - '0') != primeiroDigito) {
            return false;
        }

        // Calcula o segundo digito verificador
        soma = 0;

        for (int i = 0; i < 10; i++) {
            soma += (11 - i) * (cpf.charAt(i) - '0');
        }

        int segundoDigito = 11 - (soma % 11);

        if (segundoDigito > 9) {
            segundoDigito = 0;
        }

        // Verifica o segundo digito verificador
        if ((cpf.charAt(10) - '0') != segundoDigito) {
            return false;
        }

        return true;

    }

}