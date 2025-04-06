package com.gestao.controller;




import com.gestao.domain.Fornecedor;
import com.gestao.dto.FornecedorDTO;
import com.gestao.service.FornecedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedor")

@Tag(name = "Fornecedor", description = "Informação do Fornecedor")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @PostMapping
    @Operation(summary = "Cadastro de Fornecedores", description = "Essa função é responsável por cadastrar um fornecedor")
    public ResponseEntity<Fornecedor> criarFornecedor(@RequestBody @Valid FornecedorDTO dto) {
        Fornecedor fornecedorCriado = fornecedorService.salvar(dto);
        return ResponseEntity.status(201).body(fornecedorCriado);
    }

    @GetMapping
    public ResponseEntity<List<Fornecedor>> listarComFiltro(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpfCnpj) {

        List<Fornecedor> fornecedores = fornecedorService.filtrar(nome, cpfCnpj);
        return ResponseEntity.ok(fornecedores);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> buscarFornecedor(@PathVariable Long id) {
        return ResponseEntity.ok(fornecedorService.buscarPorId(id));
    }




    @PutMapping("/{id}")
    public ResponseEntity<Fornecedor> atualizarFornecedor(@PathVariable Long id, @RequestBody @Valid FornecedorDTO dto) {

        Fornecedor fornecedorAtualizado =   this.fornecedorService.atualizarFornecedor(id,dto);
        return ResponseEntity.ok(fornecedorAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarFornecedor(@PathVariable Long id) {
        fornecedorService.deletarFornecedor(id);
        return ResponseEntity.ok("Fornecedor deletado com sucesso.");
    }
}