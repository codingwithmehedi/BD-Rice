package com.mehedi.bdrice.ui.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mehedi.bdrice.R;
import com.mehedi.bdrice.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false  );
        View view = binding.getRoot();
      /*  // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_home, container, false );
        binding = FragmentHomeBinding.bind( view );*/





      return view;
    }
}