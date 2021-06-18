package com.mehedi.bdrice.ui.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mehedi.bdrice.R;
import com.mehedi.bdrice.databinding.FragmentCartBinding;

public class CartFragment extends Fragment {
    private FragmentCartBinding binding;


    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_cart, container, false );
        View view = binding.getRoot();


    return view;
    }
}