package com.example.sgm;

import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@HiltViewModel
public class SGMViewModel extends ViewModel {
    private final SGMRepository repository;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final MutableLiveData<List<Cliente>> clientes = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<Veiculo>> veiculos = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> carregando = new MutableLiveData<>(false);
    private final MutableLiveData<String> mensagem = new MutableLiveData<>();
    private final AtomicInteger operacoesAtivas = new AtomicInteger(0);

    private void iniciarOperacao() { if (operacoesAtivas.incrementAndGet() == 1) carregando.postValue(true); }
    private void finalizarOperacao() { if (operacoesAtivas.decrementAndGet() <= 0) { operacoesAtivas.set(0); carregando.postValue(false); } }

    @Inject public SGMViewModel(SGMRepository repository) {
        this.repository = repository;
        atualizarClientes();
        atualizarVeiculos();
    }
    public LiveData<List<Cliente>> getClientes() { return clientes; }
    public LiveData<List<Veiculo>> getVeiculos() { return veiculos; }
    public LiveData<Boolean> getCarregando() { return carregando; }
    public LiveData<String> getMensagem() { return mensagem; }

    public void atualizarClientes() {
        iniciarOperacao();
        executor.execute(() -> { try { clientes.postValue(repository.listarClientes()); } catch (Exception e) { mensagem.postValue("Erro ao carregar clientes: " + e.getMessage()); } finally { finalizarOperacao(); } });
    }
    public void atualizarVeiculos() {
        iniciarOperacao();
        executor.execute(() -> { try { veiculos.postValue(repository.listarVeiculos()); } catch (Exception e) { mensagem.postValue("Erro ao carregar veículos: " + e.getMessage()); } finally { finalizarOperacao(); } });
    }
    public void salvarCliente(Cliente c) {
        iniciarOperacao(); executor.execute(() -> { try { repository.salvarCliente(c); clientes.postValue(repository.listarClientes()); mensagem.postValue("Cliente salvo com sucesso."); } catch (Exception e) { mensagem.postValue("Erro ao salvar cliente: " + e.getMessage()); } finally { finalizarOperacao(); } });
    }
    public void excluirCliente(Cliente c) {
        iniciarOperacao(); executor.execute(() -> { try { repository.excluirCliente(c); clientes.postValue(repository.listarClientes()); mensagem.postValue("Cliente excluído."); } catch (Exception e) { mensagem.postValue("Erro ao excluir cliente: " + e.getMessage()); } finally { finalizarOperacao(); } });
    }
    public void salvarVeiculo(Veiculo v) {
        iniciarOperacao(); executor.execute(() -> { try { repository.salvarVeiculo(v); veiculos.postValue(repository.listarVeiculos()); mensagem.postValue("Veículo salvo com sucesso."); } catch (Exception e) { mensagem.postValue("Erro ao salvar veículo: " + e.getMessage()); } finally { finalizarOperacao(); } });
    }
    public void excluirVeiculo(Veiculo v) {
        iniciarOperacao(); executor.execute(() -> { try { repository.excluirVeiculo(v); veiculos.postValue(repository.listarVeiculos()); mensagem.postValue("Veículo excluído."); } catch (Exception e) { mensagem.postValue("Erro ao excluir veículo: " + e.getMessage()); } finally { finalizarOperacao(); } });
    }
    @Override protected void onCleared() { super.onCleared(); executor.shutdown(); }
}
