package com.example.sgm;
import dagger.hilt.android.AndroidEntryPoint;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.sgm.databinding.ActivityMainBinding;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements InicioFragment.OnInicioActionListener {
    private ActivityMainBinding binding;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnInicio.setOnClickListener(v -> abrir(new InicioFragment(), false));
        binding.btnClientes.setOnClickListener(v -> abrir(new ClientesFragment(), false));
        binding.btnVeiculos.setOnClickListener(v -> abrir(new VeiculosFragment(), false));
        binding.btnSobre.setOnClickListener(v -> abrir(new SobreFragment(), false));
        if (savedInstanceState == null) abrir(new InicioFragment(), false);
    }
    private void abrir(Fragment fragment, boolean voltar) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment);
        if (voltar) transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override public void abrirClientes() { abrir(new ClientesFragment(), true); }
    @Override public void abrirVeiculos() { abrir(new VeiculosFragment(), true); }
    @Override public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) manager.popBackStack(); else super.onBackPressed();
    }
    @Override protected void onDestroy() { super.onDestroy(); binding = null; }
}
