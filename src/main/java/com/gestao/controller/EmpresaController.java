package com.gestao.controller;


import com.gestao.domain.CreateFornecedorRequest;
import com.gestao.domain.Empresa;
import com.gestao.domain.Fornecedor;
import com.gestao.dto.EmpresaDTO;
import com.gestao.infra.exceptions.RegraNegocioException;
import com.gestao.repository.EmpresaRepository;
import com.gestao.repository.FornecedorRepository;
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
    @Autowired
    EmpresaRepository empresaRepository;



    @PostMapping
    public ResponseEntity<Empresa> criar(@RequestBody EmpresaDTO dto) {

     Empresa empresa=   this.empresaService.salvar(dto);
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
    public ResponseEntity<Empresa> atualizarEmpresa(@PathVariable Long id, @RequestBody EmpresaDTO dto) {

        Empresa empresaAtualizado=   this.empresaService.atualizarEmpresa(id,dto);
        return ResponseEntity.ok(empresaAtualizado);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEmpresa(@PathVariable Long id) {
        empresaService.deletarEmpresa(id);
        return ResponseEntity.noContent().build();
    }




}
