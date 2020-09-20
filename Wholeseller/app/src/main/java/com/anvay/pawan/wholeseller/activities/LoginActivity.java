/**
 * coded by Pawan Singh Harariya
 */
package com.anvay.pawan.wholeseller.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anvay.pawan.wholeseller.R;
import com.anvay.pawan.wholeseller.utils.Constants;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private int RC_SIGN_IN = 2020;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AuthUI.IdpConfig> providers = Collections.singletonList(new AuthUI.IdpConfig.PhoneBuilder().build());
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .setAvailableProviders(providers)
//                                .setLogo(R.drawable.button_background)
//                                .setTheme(R.style.)      // Set theme
                                .build(), RC_SIGN_IN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                assert user != null;
                editor.putString(Constants.MOBILE_NUMBER, user.getPhoneNumber());
                editor.putString(Constants.FIREBASE_ID, user.getUid());
                editor.putBoolean(Constants.LOGIN_STATUS, true);
                editor.apply();
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            } else {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}