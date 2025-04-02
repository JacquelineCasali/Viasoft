package com.gestao.controller;

import com.gestao.domain.Empresa;
import com.gestao.domain.Fornecedor;
import com.gestao.service.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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


    public ResponseEntity <Empresa>criarEmpresa ( @RequestBody Empresa empresa){
       this.empresaService.criarEmpresa(empresa);
           return ResponseEntity.status(HttpStatus.CREATED).body(empresa);
    }
    @GetMapping
    public ResponseEntity<List<Empresa>>getAllEmpresa () {
        List<Empresa> empresas= this.empresaService.getAllEmpresa();
        return new ResponseEntity<>(empresas,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Empresa> buscarEmpresa(@PathVariable Long id) {
        return ResponseEntity.ok(empresaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> atualizarEmpresa(@PathVariable Long id, @RequestBody Empresa empresa) {
        return ResponseEntity.ok(empresaService.atualizarEmpresa(id, empresa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEmpresa(@PathVariable Long id) {
        empresaService.deletarEmpresa(id);
        return ResponseEntity.noContent().build();
    }
    }


