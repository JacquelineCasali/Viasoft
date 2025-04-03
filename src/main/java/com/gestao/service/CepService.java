package com.gestao.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CepService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String CEP_API_URL = "https://viacep.com.br/ws/{cep}/json/";

    public Map<String, Object> buscarCep(String cep) {
        ResponseEntity<Map> response = restTemplate.getForEntity(CEP_API_URL, Map.class, cep);

        // Verifica se a resposta contém erro (ViaCEP retorna "erro": true se não encontrar)
        if (response.getBody() == null || response.getBody().containsKey("erro")) {
            throw new RuntimeException("CEP não encontrado ou inválido.");
        }

        return response.getBody();
    }
}