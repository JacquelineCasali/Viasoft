package com.gestao.controller;


import com.gestao.domain.fornecedor.Fornecedor;

import com.gestao.service.FornecedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    public ResponseEntity create (@Valid @RequestBody Fornecedor fornecedor){

       this.fornecedorService.saveFornecedor(fornecedor);
           return ResponseEntity.status(HttpStatus.CREATED).body(fornecedor);

    }
    @GetMapping

    @Operation(summary = "Lista de Fornecedores", description = "Essa função é responsável por listar os fornecedores")

    public ResponseEntity <List<Fornecedor>> getAllFornecedor(){
        List<Fornecedor> fornecedores= this.fornecedorService.getAllFornecedor();
        return new ResponseEntity<>(fornecedores,HttpStatus.OK);
    }

    }


