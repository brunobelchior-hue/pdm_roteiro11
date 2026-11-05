package com.example.sgm;
import dagger.hilt.android.AndroidEntryPoint;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.sgm.databinding.FragmentClientesBinding;

@AndroidEntryPoint
public class ClientesFragment extends Fragment {
    private FragmentClientesBinding binding;
    private SGMViewModel viewModel;

    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentClientesBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(SGMViewModel.class);
        binding.btnSalvarCliente.setOnClickListener(v -> {
            String nome = binding.edtNome.getText().toString().trim();
            String telefone = binding.edtTelefone.getText().toString().trim();
            if (nome.isEmpty() || telefone.isEmpty()) { Toast.makeText(requireContext(), "Preencha nome e telefone.", Toast.LENGTH_SHORT).show(); return; }
            viewModel.salvarCliente(new Cliente(nome, telefone));
            binding.edtNome.setText(""); binding.edtTelefone.setText("");
        });
        viewModel.getClientes().observe(getViewLifecycleOwner(), lista -> {
            if (binding == null) return;
            LinearLayout area = binding.txtClientes; area.removeAllViews();
            if (lista == null || lista.isEmpty()) { TextView vazio = new TextView(requireContext()); vazio.setText("Nenhum cliente cadastrado."); area.addView(vazio); return; }
            for (Cliente cliente : lista) {
                LinearLayout item = new LinearLayout(requireContext()); item.setOrientation(LinearLayout.VERTICAL); item.setPadding(0, 16, 0, 16);
                TextView info = new TextView(requireContext()); info.setText("Cliente: " + cliente.nome + "\nTelefone: " + cliente.telefone); info.setTextSize(16); item.addView(info);
                LinearLayout acoes = new LinearLayout(requireContext());
                Button editar = new Button(requireContext()); editar.setText("Editar"); editar.setOnClickListener(v -> editarCliente(cliente));
                Button excluir = new Button(requireContext()); excluir.setText("Excluir"); excluir.setOnClickListener(v -> new AlertDialog.Builder(requireContext()).setTitle("Excluir cliente").setMessage("Deseja excluir " + cliente.nome + "?").setNegativeButton("Cancelar", null).setPositiveButton("Excluir", (d,w) -> viewModel.excluirCliente(cliente)).show());
                acoes.addView(editar, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1)); acoes.addView(excluir, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1)); item.addView(acoes); area.addView(item);
                View linha = new View(requireContext()); linha.setBackgroundColor(0xFFDDDDDD); area.addView(linha, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
            }
        });
        viewModel.getCarregando().observe(getViewLifecycleOwner(), carregando -> { if (binding != null) { binding.progressLoading.setVisibility(Boolean.TRUE.equals(carregando) ? View.VISIBLE : View.GONE); binding.btnSalvarCliente.setEnabled(!Boolean.TRUE.equals(carregando)); } });
        viewModel.getMensagem().observe(getViewLifecycleOwner(), mensagem -> { if (mensagem != null && !mensagem.isEmpty()) Toast.makeText(requireContext(), mensagem, Toast.LENGTH_SHORT).show(); });
        return binding.getRoot();
    }
    private void editarCliente(Cliente cliente) {
        LinearLayout layout = new LinearLayout(requireContext()); layout.setOrientation(LinearLayout.VERTICAL); layout.setPadding(40, 8, 40, 0);
        EditText nome = new EditText(requireContext()); nome.setHint("Nome"); nome.setText(cliente.nome); nome.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS); layout.addView(nome);
        EditText telefone = new EditText(requireContext()); telefone.setHint("Telefone"); telefone.setText(cliente.telefone); telefone.setInputType(InputType.TYPE_CLASS_PHONE); layout.addView(telefone);
        new AlertDialog.Builder(requireContext()).setTitle("Editar cliente").setView(layout).setNegativeButton("Cancelar", null).setPositiveButton("Salvar", (d,w) -> { String n=nome.getText().toString().trim(), t=telefone.getText().toString().trim(); if(n.isEmpty()||t.isEmpty()){Toast.makeText(requireContext(),"Preencha todos os campos.",Toast.LENGTH_SHORT).show();return;} cliente.nome=n; cliente.telefone=t; viewModel.salvarCliente(cliente); }).show();
    }
    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}
