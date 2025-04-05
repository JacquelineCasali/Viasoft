package com.gestao.utils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestao.infra.exceptions.RegraNegocioException;

public class CepUtils {

    public static String buscarUfPorCep(String cep) {
        try {
            URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() != 200) {
                throw new RegraNegocioException("Erro ao consultar o ViaCEP: HTTP " + conn.getResponseCode());
            }

            Scanner scanner = new Scanner(conn.getInputStream());
            String response = scanner.useDelimiter("\\A").next();
            scanner.close();

            System.out.println("Resposta ViaCEP: " + response); // debug

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response);

            if (jsonNode.has("erro") && jsonNode.get("erro").asBoolean()) {
                throw new RegraNegocioException("CEP inválido.");
            }

            return jsonNode.get("uf").asText();

        } catch (IOException e) {
            throw new RuntimeException("Erro ao buscar o estado pelo CEP: " + e.getMessage(), e);
        }
    }
}