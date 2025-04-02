package com.gestao.service;
import com.gestao.domain.Empresa;
import com.gestao.domain.EmpresaFornecedor;
import com.gestao.domain.Fornecedor;
import com.gestao.repository.EmpresaFornecedorRepository;
import com.gestao.repository.EmpresaRepository;
import com.gestao.repository.FornecedorRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class EmpresaFornecedorService {

    private final EmpresaFornecedorRepository empresaFornecedorRepository;
    private final EmpresaRepository empresaRepository;
    private final FornecedorRepository fornecedorRepository;

    public EmpresaFornecedor associarEmpresaFornecedor(Long empresaId, Long fornecedorId) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));

        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId)
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado"));

        EmpresaFornecedor empresaFornecedor = new EmpresaFornecedor();
        empresaFornecedor.setEmpresa(empresa);
        empresaFornecedor.setFornecedor(fornecedor);

        return empresaFornecedorRepository.save(empresaFornecedor);
    }

}
