package com.example.sgm;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
@Singleton
public class SGMRepository {
    private final ClienteDao clientes;
    private final VeiculoDao veiculos;
    @Inject public SGMRepository(ClienteDao clientes, VeiculoDao veiculos) {
        this.clientes = clientes;
        this.veiculos = veiculos;
    }
    public List<Cliente> listarClientes() { return clientes.listar(); }
    public List<Veiculo> listarVeiculos() { return veiculos.listar(); }
    public void salvarCliente(Cliente c) { if (c.id == 0) clientes.inserir(c); else clientes.atualizar(c); }
    public void salvarVeiculo(Veiculo v) { if (v.id == 0) veiculos.inserir(v); else veiculos.atualizar(v); }
    public void excluirCliente(Cliente c) { clientes.excluir(c); }
    public void excluirVeiculo(Veiculo v) { veiculos.excluir(v); }
}
