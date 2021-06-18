package com.mehedi.bdrice.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mehedi.bdrice.ui.fragments.AccountFragment;
import com.mehedi.bdrice.ui.fragments.CartFragment;
import com.mehedi.bdrice.ui.fragments.HomeFragment;
import com.mehedi.bdrice.ui.fragments.MessageFragment;
import com.mehedi.bdrice.R;

public class RiceActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_rice );

        bottomNavigationView = findViewById( R.id.bottom_navigationView );
        bottomNavigationView.setOnNavigationItemSelectedListener( navigationItemSelectedListener );
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentsContainer,new HomeFragment()).commit();
        }
    }
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.homeFragment:
                    getSupportFragmentManager().beginTransaction().replace( R.id.fragmentsContainer,new HomeFragment() ).commit();
                    return true;
                case R.id.messageFragment:
                    getSupportFragmentManager().beginTransaction().replace( R.id.fragmentsContainer,new MessageFragment() ).commit();
                    return true;
                case R.id.cartFragment:
                    getSupportFragmentManager().beginTransaction().replace( R.id.fragmentsContainer,new CartFragment() ).commit();
                    return true;
                case R.id.accountFragment:
                    getSupportFragmentManager().beginTransaction().replace( R.id.fragmentsContainer,new AccountFragment() ).commit();
                    return true;
            }

            return false;
        }
    };
}