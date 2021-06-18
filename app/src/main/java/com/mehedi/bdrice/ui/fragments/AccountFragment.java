package com.mehedi.bdrice.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mehedi.bdrice.R;
import com.mehedi.bdrice.databinding.FragmentAccountBinding;
import com.mehedi.bdrice.ui.Auth.LoginActivity;

public class AccountFragment extends Fragment {
  private FragmentAccountBinding binding;


    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

             binding = DataBindingUtil.inflate( inflater,R.layout.fragment_account,container,false );
             View view = binding.getRoot();

        binding.btnLoginOrReg.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //startActivity( new Intent( getContext(), LoginActivity.class ) );
                Intent signIn = new Intent( getContext(), LoginActivity.class );
                startActivity( signIn );

            }
        } );

    return view;
    }

}