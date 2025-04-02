package com.gestao.controller;

import com.gestao.domain.empresa.Empresa;
import com.gestao.service.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresa")

@Tag(name = "Empresa", description = "Informação da Empresa")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @PostMapping
    @Operation(summary = "Cadastro de usuário", description = "Essa função é responsável por cadastrar um usuário")


    public ResponseEntity create (@Valid @RequestBody Empresa empresa){

       this.empresaService.saveEmpresa(empresa);
           return ResponseEntity.status(HttpStatus.CREATED).body(empresa);

    }
    @GetMapping

    @Operation(summary = "Lista de usuários", description = "Essa função é responsável por listar os usuários")

    public ResponseEntity <List<Empresa>> getAllUsers(){
        List<Empresa> users= this.empresaService.getAllEmpresas();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    }


