package com.example.sgm;
import dagger.hilt.android.AndroidEntryPoint;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.sgm.databinding.FragmentSobreBinding;

@AndroidEntryPoint
public class SobreFragment extends Fragment {
    private FragmentSobreBinding binding;
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSobreBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}
