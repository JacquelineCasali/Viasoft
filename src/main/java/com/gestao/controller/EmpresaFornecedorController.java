package com.gestao.controller;

import com.gestao.domain.EmpresaFornecedor;
import com.gestao.service.EmpresaFornecedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/empresafornecedor")
@RequiredArgsConstructor
public class EmpresaFornecedorController {
    @Autowired
    private final EmpresaFornecedorService empresaFornecedorService;
    @PostMapping("/{empresaId}/{fornecedorId}")
    public ResponseEntity<EmpresaFornecedor> associarEmpresaFornecedor(
            @PathVariable Long empresaId,
            @PathVariable Long fornecedorId) {

        EmpresaFornecedor empresaFornecedor = empresaFornecedorService.associarEmpresaFornecedor(empresaId, fornecedorId);
        return ResponseEntity.ok(empresaFornecedor);
    }

}
