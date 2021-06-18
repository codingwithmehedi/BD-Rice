package com.mehedi.bdrice.ui.Auth;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mehedi.bdrice.R;
import com.mehedi.bdrice.databinding.ActivityLoginBinding;
import com.mehedi.bdrice.ui.activity.RiceActivity;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {


    private static final String TAG= "Login Activity";
    private ActivityLoginBinding binding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_login );

        binding.btnBackLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( LoginActivity.this, RiceActivity.class ) );
            }
        } );

        binding.btnLoginUser.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogIn();
            }
        } );

        binding.BtnRegisterUser.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( LoginActivity.this, SignUpActivity.class ) );
                finish();
            }
        } );


        mAuth = FirebaseAuth.getInstance();
        binding.editLoginEmail.addTextChangedListener( mEmailWatcher );
        binding.editLoginPassword.addTextChangedListener( mPasswordWatcher );

        checkValidation();
    }

    private void checkValidation() {
        String emailInput = binding.editLoginEmail.getText().toString().trim();
        String passwordInput = binding.editLoginPassword.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher( emailInput ).matches() || passwordInput.length()<=5){
            binding.btnLoginUser.setEnabled( false );
        }else {
            binding.btnLoginUser.setEnabled( true );
        }

    }

    private void LogIn() {

        String email = binding.editLoginEmail.getText().toString().trim();
        String password = binding.editLoginPassword.getText().toString().trim();

        if (Patterns.EMAIL_ADDRESS.matcher( email ).matches() && password.length()>6){

            binding.ProgressLogin.setVisibility( View.VISIBLE );
            binding.ProgressLogin.setTag( "Please Wait! We are checking your credential" );
            binding.layoutLoginInfo.setVisibility( View.GONE );

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user.isEmailVerified()){
                                    Toast.makeText( LoginActivity.this, "Logged in Successful", Toast.LENGTH_SHORT ).show();
                                    startActivity( new Intent( getApplicationContext(), RiceActivity.class ) );
                                    finish();
                                    //   Intent logIN = new Intent();
                                    binding.ProgressLogin.setVisibility( View.GONE );
                                }else {
                                    user.sendEmailVerification();
                                    Toast.makeText( LoginActivity.this, "Check your email to verify your account!", Toast.LENGTH_SHORT ).show();
                                    //   Intent logIN = new Intent();
                                    binding.ProgressLogin.setVisibility( View.GONE );
                                    binding.layoutLoginInfo.setVisibility( View.VISIBLE );
                                }
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                //   Intent logIN = new Intent();
                                binding.ProgressLogin.setVisibility( View.GONE );
                                binding.layoutLoginInfo.setVisibility( View.VISIBLE );
                                // updateUI(null);
                            }

                        }
                    });
        }else {
            Toast.makeText( this, "Please check your credential!", Toast.LENGTH_SHORT ).show();
            //   Intent logIN = new Intent();
            binding.ProgressLogin.setVisibility( View.GONE );
            binding.layoutLoginInfo.setVisibility( View.VISIBLE );
        }



    }

    //text watcher for email check
    private TextWatcher mEmailWatcher= new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String inputEmail = binding.editLoginEmail.getText().toString().trim();
            if (s.length()>=12){
                if (inputEmail.isEmpty()){
                    binding.layoutInputEmail.setError( "Email can't be empty." );
                }else if (inputEmail.length()>=15 && !Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()){
                    binding.layoutInputEmail.setError( "Enter a valid email address" );
                }else {
                    binding.layoutInputEmail.setErrorEnabled( false );
                }
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
            checkValidation();
        }
    };

    //text watcher for password
    private  TextWatcher mPasswordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String inputPassword = binding.editLoginPassword.getText().toString().trim();

            if (s.length()>=0){
                if (inputPassword.isEmpty()){
                    binding.layoutInputPassword.setError( "Password can't be empty." );
                }else if (inputPassword.length()<=5){
                    binding.layoutInputPassword.setError( "Password must be 6-20 characters" );
                }else {
                    binding.layoutInputPassword.setErrorEnabled( false );
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            checkValidation();
        }
    };

}