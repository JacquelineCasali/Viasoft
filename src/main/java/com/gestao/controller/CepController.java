package com.gestao.controller;

import com.gestao.service.CepService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/cep")
@RequiredArgsConstructor
public class CepController {

    private final CepService cepService;

    @GetMapping("/{cep}")
    public ResponseEntity<Map<String, Object>> buscarCep(@PathVariable String cep) {
        Map<String, Object> dadosCep = cepService.buscarCep(cep);
        return ResponseEntity.ok(dadosCep);
    }
}