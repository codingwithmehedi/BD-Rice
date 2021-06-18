package com.mehedi.bdrice.ui.Auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mehedi.bdrice.R;
import com.mehedi.bdrice.databinding.ActivitySignUpBinding;
import com.mehedi.bdrice.ui.activity.RiceActivity;
import com.mehedi.bdrice.ui.fragments.DivisionAlertDialog;

import java.util.HashMap;
import java.util.regex.Pattern;

import static com.mehedi.bdrice.R.color.colorRed;

public class SignUpActivity extends AppCompatActivity implements DivisionAlertDialog.SingleChoiceListener {
    private static final String TAG = "Sign up Activity";
    private ActivitySignUpBinding binding;

    private FirebaseAuth mAuth;
    private DatabaseReference userDbRef;
    private String userID;

    ProgressDialog progressDialog;

    // defining our own password pattern
    private static final Pattern PASSWORD_PATTERN =
           Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +       //at least 1 lower case letter
                    //"(?=.*[A-Z])" +       //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this,R.layout.activity_sign_up );

        binding.RegisterBack.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( SignUpActivity.this,LoginActivity.class ) );
                SignUpActivity.this.finish();
            }
        } );

        binding.btnSelectDivision.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DialogFragment dialogFragment = new DivisionAlertDialog();
               dialogFragment.setCancelable( false );
               dialogFragment.show( getSupportFragmentManager(),"Division Select Dialog" );
            }
        } );

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog( this );

        binding.editFirstName.addTextChangedListener( mFirstNameWatcher );
        binding.editLastName.addTextChangedListener( mLastNameWatcher );
        binding.editTextCompanyName.addTextChangedListener( mCompanyNameWatcher );
        binding.editTextEmail.addTextChangedListener( mEmailTextWatcher );
        binding.editTextPassword.addTextChangedListener( mPasswordWatcher );



        binding.btnSUNext.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }


        } );


    }

    private void checkValidation() {

        String inputDivision = binding.tvDivision.getText().toString().trim();
        String inputFirstName = binding.editFirstName.getText().toString().trim();
        String inputLastName = binding.editLastName.getText().toString().trim();
        String inputCompanyName = binding.editTextCompanyName.getText().toString().trim();
        String inputEmail = binding.editTextEmail.getText().toString().trim();
        String inputPassword = binding.editFirstName.getText().toString().trim();

            if (binding.cbNonBusiness.isChecked()){
                    if (inputDivision.isEmpty()
                            ||inputFirstName.length()<=3
                            ||inputLastName.length()<=2
                            ||!Patterns.EMAIL_ADDRESS.matcher( inputEmail ).matches()
                            ||inputPassword.length()<=5
                            ||inputPassword.length()>=20){
                        binding.btnSUNext.setEnabled( false );
                    }else {
                        binding.btnSUNext.setEnabled( true );
                    }

                }
            else {
                        if ( inputDivision.isEmpty()
                        ||inputFirstName.length()<=3
                        ||inputLastName.length()<=2
                        ||inputCompanyName.length()<=10
                        ||inputCompanyName.length()>=41
                        ||!Patterns.EMAIL_ADDRESS.matcher( inputEmail ).matches()
                        ||inputPassword.length()<=5
                        ||inputPassword.length()>=20){
                            binding.btnSUNext.setEnabled( false );
                        }else {
                            binding.btnSUNext.setEnabled( true );
                        }
            }



    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
       // updateUI(currentUser);
    }
    public void onCheckboxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()) {
            case R.id.cbNonBusiness:
                if (!checked)
                    binding.layoutCompanyName.setVisibility( View.VISIBLE );
                if (checked)
                    binding.layoutCompanyName.setVisibility( View.GONE );
                    binding.editTextCompanyName.getText().clear();

        }
    }

    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        binding.tvDivision.setText( list[position] );
        Toast.makeText(this, list[position], Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNegativeButtonClicked() {
        binding.tvDivision.setText(R.string.select_division);
        Toast.makeText(this, R.string.select_division, Toast.LENGTH_SHORT).show();
      //startActivity( new Intent( SignUpActivity.this,SignUpActivity.class ) );
    }

    // First name
    private TextWatcher mFirstNameWatcher= new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {
               String companyName = binding.editFirstName.getText().toString().trim();
               if (companyName.isEmpty()){
                   binding.editFirstName.setError( "First Name can't be empty");
               }

               checkValidation();
           }
       };
    //last name
    private TextWatcher mLastNameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String companyName = binding.editLastName.getText().toString().trim();
            if (companyName.isEmpty()){
                binding.editLastName.setError( "Last Name can't be empty");
            }
            checkValidation();
        }
    };
    //company name
    private TextWatcher mCompanyNameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String companyName = binding.editTextCompanyName.getText().toString().trim();
            if (s.length()>=0 && s.length()<=40){
                binding.layoutCompanyName.setErrorEnabled( false );
                if (companyName.length() >=3 && companyName.length()<=10 ){
                    binding.layoutCompanyName.setHelperText( "Must be at least 10-40 characters");
                }else {
                    binding.layoutCompanyName.setHelperTextEnabled( false );
                }
            }else {
            binding.layoutCompanyName.setError( "Name is too long. Name should be 10-40 characters" );
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            String companyName = binding.editTextCompanyName.getText().toString().trim();
            if (companyName.isEmpty()){
                binding.layoutCompanyName.setHelperText( "Must be an alphabetic character");
            }

            checkValidation();
        }
    };
    //email checking by text watcher
    private TextWatcher  mEmailTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
                 if (binding.editTextEmail.getText().toString().trim().isEmpty()){
                     binding.layoutEmail.setError( "Email can't be empty" );
                 }

                 if (s.length()>=11){
                     if(!Patterns.EMAIL_ADDRESS.matcher( binding.editTextEmail.getText().toString()).matches()){
                         binding.layoutEmail.setErrorEnabled( true );
                         binding.layoutEmail.setError( "Please enter a valid email" );
                     }else{
                         binding.layoutEmail.setErrorEnabled( false );
                     }
                 }


            checkValidation();
        }
    };
    //password checking by text watcher
    private TextWatcher mPasswordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


            if (s.length()>=5 && s.length()<=20){
                String passwordInput = binding.editTextPassword.getText().toString().trim();
                if (passwordInput.length()>=6 && !PASSWORD_PATTERN.matcher(passwordInput).matches() && passwordInput.length()<=6) {
                    binding.layoutPassword.setHelperText("Password is too weak.");
                    
                } else if (passwordInput.length()>=8 && PASSWORD_PATTERN.matcher(passwordInput).matches() && passwordInput.length()<=20 ){
                        binding.layoutPassword.setErrorEnabled(false);
                        binding.layoutPassword.setHelperText( "Strong password!");
                        //  binding.layoutPassword.setHelperTextColor(ColorStateList.valueOf( R.color.colorGreen ) );
                    }
                    else {
                        binding.layoutPassword.setErrorEnabled(false);
                    }

            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            String passwordInput = binding.editTextPassword.getText().toString().trim();
            if (passwordInput.isEmpty()) {
                binding.layoutPassword.setError("Password can't be empty");
            }else {
                binding.layoutPassword.setErrorEnabled( false );
            }
            checkValidation();
        }
    };

    private void SignUp() {


        progressDialog.setTitle( "Please wait! Register on process" );
        progressDialog.show();

        final String inputDivision = binding.tvDivision.getText().toString().trim();
        final String inputFirstName = binding.editFirstName.getText().toString().trim();
        String inputFirstNameLowerCase = binding.editFirstName.getText().toString().trim();
        final String inputLastName = binding.editLastName.getText().toString().trim();
        final String inputCompanyName = binding.editTextCompanyName.getText().toString().trim();
        final String inputCompanyNameLowerCase = binding.editTextCompanyName.getText().toString().toLowerCase().trim();
        final String inputCompanyNameUpperCase = binding.editTextCompanyName.getText().toString().toUpperCase().trim();
        final String email = binding.editTextEmail.getText().toString().trim();
        final String password = binding.editTextPassword.getText().toString().trim();


        userDbRef = FirebaseDatabase.getInstance().getReference("user");
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                               userID = user.getUid();
                            // updateUI(user);

                            HashMap<Object, String> hashMap = new HashMap<>();
                            hashMap.put( "division", inputDivision );
                            hashMap.put( "firstName", inputFirstName );
                            hashMap.put( "lastName", inputLastName);
                            hashMap.put( "inputCompanyON", inputCompanyName );
                            hashMap.put( "inputCompanyLC", inputCompanyNameLowerCase );
                            hashMap.put( "inputCompanyUC", inputCompanyNameUpperCase );
                            hashMap.put( "email", email);
                            hashMap.put( "password", password);

                            userDbRef.child( userID )
                                    .setValue( hashMap )
                                    .addOnCompleteListener( new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                startActivity( new Intent( SignUpActivity.this, RiceActivity.class ) );
                                                SignUpActivity.this.finish();
                                                Toast.makeText( SignUpActivity.this, "Registered Successful", Toast.LENGTH_SHORT ).show();
                                                progressDialog.dismiss();
                                            }else {
                                                Toast.makeText( SignUpActivity.this, "Failed Registration! Try again", Toast.LENGTH_SHORT ).show();
                                                progressDialog.dismiss();
                                            }
                                        }
                                    } );


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            //updateUI(null);
                        }

                    }
                });

    }

}