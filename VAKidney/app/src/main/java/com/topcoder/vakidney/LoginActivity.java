package com.topcoder.vakidney;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.topcoder.vakidney.Model.UserData;
import com.topcoder.vakidney.Util.DialogManager;
import com.topcoder.vakidney.Util.JsondataUtil;
import com.topcoder.vakidney.Util.LoginManager;

public class LoginActivity extends AppCompatActivity {


    private String username;
    private String password;
    private UserData currentUser;
    private EditText emailField, passwordField;
    private TextView tvEmailError, tvPasswordError, tvForgotPassword;
    private Button btnLogin, btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        currentUser= JsondataUtil.getUserData(getApplicationContext());
        username=currentUser.getUsername();
        password=currentUser.getPassword();
        emailField=findViewById(R.id.emailField);
        passwordField=findViewById(R.id.passwordField);
        tvEmailError=findViewById(R.id.emailErrorTv);
        tvPasswordError=findViewById(R.id.passwordErrorTv);
        tvForgotPassword=findViewById(R.id.forgotPasswordTv);
        btnLogin=findViewById(R.id.btnLogin);
        btnRegister =findViewById(R.id.btnSignUp);
        emailField.clearFocus();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailField.getText().toString().isEmpty()){
                    tvEmailError.setVisibility(View.VISIBLE);
                    tvPasswordError.setVisibility(View.GONE);
                    tvEmailError.setText(R.string.error_empty_email);
                }else if(passwordField.getText().toString().isEmpty()){
                    tvPasswordError.setVisibility(View.VISIBLE);
                    tvEmailError.setVisibility(View.GONE);
                    tvPasswordError.setText(R.string.error_empty_password);
                }else if((!emailField.getText().toString().equals(username)) || (!passwordField.getText().toString().equals(password))){
                    tvEmailError.setVisibility(View.VISIBLE);
                    tvEmailError.setText(R.string.error_email);
                    tvPasswordError.setVisibility(View.VISIBLE);
                    tvPasswordError.setText(R.string.error_password);
                }else if((emailField.getText().toString().equals(username)) && (passwordField.getText().toString().equals(password))){
                    LoginManager.setLoggedIn(getApplicationContext(), true);
                    NavigateHome();
                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.showOkDialog(LoginActivity.this, getString(R.string.feature_not_implemented), null);
            }
        });
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.showOkDialog(LoginActivity.this, getString(R.string.feature_not_implemented), null);
            }
        });
    }


    /**
     * Navigate to Home Screen
     */
    private void NavigateHome() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}
