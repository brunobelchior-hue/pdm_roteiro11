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
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.sgm.databinding.FragmentVeiculosBinding;

@AndroidEntryPoint
public class VeiculosFragment extends Fragment {
    private FragmentVeiculosBinding binding;
    private SGMViewModel viewModel;
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentVeiculosBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(SGMViewModel.class);
        binding.btnSalvarVeiculo.setOnClickListener(v -> {
            String marca=binding.edtMarca.getText().toString().trim(), modelo=binding.edtModelo.getText().toString().trim(), valor=binding.edtValor.getText().toString().trim();
            if(marca.isEmpty()||modelo.isEmpty()||valor.isEmpty()){Toast.makeText(requireContext(),"Preencha marca, modelo e valor.",Toast.LENGTH_SHORT).show();return;}
            viewModel.salvarVeiculo(new Veiculo(marca,modelo,valor)); binding.edtMarca.setText(""); binding.edtModelo.setText(""); binding.edtValor.setText("");
        });
        viewModel.getVeiculos().observe(getViewLifecycleOwner(), lista -> {
            if(binding==null)return; LinearLayout area=binding.txtVeiculos; area.removeAllViews();
            if(lista==null||lista.isEmpty()){TextView vazio=new TextView(requireContext());vazio.setText("Nenhum veículo cadastrado.");area.addView(vazio);return;}
            for(Veiculo veiculo:lista){
                LinearLayout item=new LinearLayout(requireContext());item.setOrientation(LinearLayout.VERTICAL);item.setPadding(0,16,0,16);
                TextView info=new TextView(requireContext());info.setText("Veículo: "+veiculo.marca+" "+veiculo.modelo+"\nValor: R$ "+veiculo.valor);info.setTextSize(16);item.addView(info);
                LinearLayout acoes=new LinearLayout(requireContext());Button editar=new Button(requireContext());editar.setText("Editar");editar.setOnClickListener(v->editarVeiculo(veiculo));Button excluir=new Button(requireContext());excluir.setText("Excluir");excluir.setOnClickListener(v->new AlertDialog.Builder(requireContext()).setTitle("Excluir veículo").setMessage("Deseja excluir "+veiculo.marca+" "+veiculo.modelo+"?").setNegativeButton("Cancelar",null).setPositiveButton("Excluir",(d,w)->viewModel.excluirVeiculo(veiculo)).show());
                acoes.addView(editar,new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1));acoes.addView(excluir,new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1));item.addView(acoes);area.addView(item);View linha=new View(requireContext());linha.setBackgroundColor(0xFFDDDDDD);area.addView(linha,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
            }
        });
        viewModel.getCarregando().observe(getViewLifecycleOwner(), carregando->{if(binding!=null){binding.progressLoading.setVisibility(Boolean.TRUE.equals(carregando)?View.VISIBLE:View.GONE);binding.btnSalvarVeiculo.setEnabled(!Boolean.TRUE.equals(carregando));}});
        viewModel.getMensagem().observe(getViewLifecycleOwner(), mensagem->{if(mensagem!=null&&!mensagem.isEmpty())Toast.makeText(requireContext(),mensagem,Toast.LENGTH_SHORT).show();});
        return binding.getRoot();
    }
    private void editarVeiculo(Veiculo veiculo){
        LinearLayout layout=new LinearLayout(requireContext());layout.setOrientation(LinearLayout.VERTICAL);layout.setPadding(40,8,40,0);
        EditText marca=new EditText(requireContext());marca.setHint("Marca");marca.setText(veiculo.marca);layout.addView(marca);
        EditText modelo=new EditText(requireContext());modelo.setHint("Modelo");modelo.setText(veiculo.modelo);layout.addView(modelo);
        EditText valor=new EditText(requireContext());valor.setHint("Valor");valor.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);valor.setText(veiculo.valor);layout.addView(valor);
        new AlertDialog.Builder(requireContext()).setTitle("Editar veículo").setView(layout).setNegativeButton("Cancelar",null).setPositiveButton("Salvar",(d,w)->{String m=marca.getText().toString().trim(),mo=modelo.getText().toString().trim(),va=valor.getText().toString().trim();if(m.isEmpty()||mo.isEmpty()||va.isEmpty()){Toast.makeText(requireContext(),"Preencha todos os campos.",Toast.LENGTH_SHORT).show();return;}veiculo.marca=m;veiculo.modelo=mo;veiculo.valor=va;viewModel.salvarVeiculo(veiculo);}).show();
    }
    @Override public void onDestroyView(){super.onDestroyView();binding=null;}
}
