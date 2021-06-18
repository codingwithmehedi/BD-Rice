package com.mehedi.bdrice.ui.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.mehedi.bdrice.R;

public class DivisionAlertDialog extends DialogFragment {
    int position =0; //default selected position

    public interface SingleChoiceListener{
        void onPositiveButtonClicked(String[] list,int position);
        void onNegativeButtonClicked();
    }
    SingleChoiceListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach( context );
                try{
                    mListener = (SingleChoiceListener)context;
                } catch (Exception e) {
//                    e.printStackTrace();
                    throw new ClassCastException( getActivity().toString()+"" );
                }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity());
        final String[] list = getActivity().getResources().getStringArray( R.array.select_division );
        builder.setTitle( "Select Division" )
                .setSingleChoiceItems( list, position, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        position = which;
                    }
                } )
                .setPositiveButton( "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onPositiveButtonClicked( list,position );

                    }
                } ).setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                        mListener.onNegativeButtonClicked();
            }
        } );
        return builder.create();
    }
}
