package br.com.haroldo.sgm.validator.cpfcnpj;

public class Cnpj {

    public static boolean validar(String cnpj) {

        // Verifica se o CNPJ tem 14 digitos
        if (cnpj.length() != 14) {
            return false;
        }

        // Calcula o primeiro digito verificador
        int soma = 0;
        int peso = 2;

        for (int i = 11; i >= 0; i--) {

            soma += (cnpj.charAt(i) - '0') * peso;
            peso++;

            if (peso == 10) {
                peso = 2;
            }

        }

        int primeiroDigito = 11 - (soma % 11);

        if (primeiroDigito > 9) {
            primeiroDigito = 0;
        }

        // Verifica o primeiro digito verificador
        if ((cnpj.charAt(12) - '0') != primeiroDigito) {
            return false;
        }

        // Calcula o segundo digito verificador
        soma = 0;
        peso = 2;

        for (int i = 12; i >= 0; i--) {

            soma += (cnpj.charAt(i) - '0') * peso;
            peso++;

            if (peso == 10) {
                peso = 2;
            }

        }

        int segundoDigito = 11 - (soma % 11);

        if (segundoDigito > 9) {
            segundoDigito = 0;
        }

        // Verifica o segundo digito verificador
        if ((cnpj.charAt(13) - '0') != segundoDigito) {
            return false;
        }

        return true;
    }

}