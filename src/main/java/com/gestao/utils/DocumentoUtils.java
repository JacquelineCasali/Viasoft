package com.gestao.utils;

public class DocumentoUtils {
    public static boolean isCnpjOuCpfValido(String documento) {
        documento = documento.replaceAll("[^0-9]", ""); // Remove pontos, traços e barras

        if (documento.length() == 11) {
            return isCpfValido(documento);
        } else if (documento.length() == 14) {
            return isCnpjValido(documento);
        }
        return false;
    }

    private static boolean isCpfValido(String cpf) {
        if (cpf.matches("(\\d)\\1{10}")) return false; // CPF com todos os dígitos iguais é inválido

        int[] pesosPrimeiroDigito = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesosSegundoDigito = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        return verificaDigito(cpf, pesosPrimeiroDigito, 9) && verificaDigito(cpf, pesosSegundoDigito, 10);
    }

    private static boolean isCnpjValido(String cnpj) {
        if (cnpj.matches("(\\d)\\1{13}")) return false; // CNPJ com todos os dígitos iguais é inválido

        int[] pesosPrimeiroDigito = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesosSegundoDigito = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        return verificaDigito(cnpj, pesosPrimeiroDigito, 12) && verificaDigito(cnpj, pesosSegundoDigito, 13);
    }

    private static boolean verificaDigito(String doc, int[] pesos, int posicao) {
        int soma = 0;
        for (int i = 0; i < pesos.length; i++) {
            soma += (doc.charAt(i) - '0') * pesos[i];
        }
        int resto = soma % 11;
        int digitoCalculado = (resto < 2) ? 0 : (11 - resto);
        return digitoCalculado == (doc.charAt(posicao) - '0');
    }
}