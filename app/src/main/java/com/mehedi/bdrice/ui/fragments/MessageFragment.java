package com.mehedi.bdrice.ui.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mehedi.bdrice.R;
import com.mehedi.bdrice.databinding.FragmentMessageBinding;

public class MessageFragment extends Fragment{
    private FragmentMessageBinding binding;

    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate( inflater,R.layout.fragment_message, container, false  );
        View view = binding.getRoot();

        // Inflate the layout for this fragment
        //return inflater.inflate( R.layout.fragment_message, container, false );

    return view;
    }
}