package com.viasoft.enums;

public enum ProvedorIntegracao {
    AWS, OCI;

    public static ProvedorIntegracao from(String value) {
        try {
            return ProvedorIntegracao.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Provedor de integração inválido: " + value);
        }
    }
}
