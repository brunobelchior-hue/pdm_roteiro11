package com.example.sgm;
import dagger.hilt.android.AndroidEntryPoint;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.sgm.databinding.FragmentInicioBinding;

@AndroidEntryPoint
public class InicioFragment extends Fragment {
    public interface OnInicioActionListener { void abrirClientes(); void abrirVeiculos(); }
    private FragmentInicioBinding binding;
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInicioBinding.inflate(inflater, container, false);
        binding.btnInicioClientes.setOnClickListener(v -> { if (getActivity() instanceof OnInicioActionListener) ((OnInicioActionListener)getActivity()).abrirClientes(); });
        binding.btnInicioVeiculos.setOnClickListener(v -> { if (getActivity() instanceof OnInicioActionListener) ((OnInicioActionListener)getActivity()).abrirVeiculos(); });
        return binding.getRoot();
    }
    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}
