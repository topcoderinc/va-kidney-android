package com.topcoder.vakidney;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.topcoder.vakidney.model.UserData;
import com.topcoder.vakidney.util.DialogManager;
import com.topcoder.vakidney.util.JsondataUtil;
import com.topcoder.vakidney.util.LoginManager;

/**
 * This class is used to perform logging in task
 */
public class LoginActivity extends AppCompatActivity {

    private String username;
    private String password;
    private UserData currentUser;
    private EditText emailField, passwordField;
    private TextView tvEmailError, tvPasswordError, tvForgotPassword;
    private RelativeLayout emailLayout, passwordLayout;
    private Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        currentUser = JsondataUtil.getUserData(getApplicationContext());
        username = currentUser.getUsername();
        password = currentUser.getPassword();
        emailLayout=findViewById(R.id.emailLayout);
        passwordLayout=findViewById(R.id.passwordLayout);
        emailField=findViewById(R.id.emailField);
        passwordField=findViewById(R.id.passwordField);
        tvEmailError=findViewById(R.id.emailErrorTv);
        tvPasswordError=findViewById(R.id.passwordErrorTv);
        tvForgotPassword=findViewById(R.id.forgotPasswordTv);
        btnLogin=findViewById(R.id.btnLogin);
        btnLogin.setEnabled(false);
        btnRegister =findViewById(R.id.btnSignUp);
        emailField.clearFocus();

        EnableDisableLogin();

        emailField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    tvEmailError.setVisibility(View.GONE);
                    emailLayout.setBackgroundResource(R.drawable.bg_login_field_normal);
                }
            }
        });

        passwordField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    tvPasswordError.setVisibility(View.GONE);
                    passwordLayout.setBackgroundResource(R.drawable.bg_login_field_normal);
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailField.getText().toString().isEmpty()){
                    tvEmailError.setVisibility(View.VISIBLE);
                    tvPasswordError.setVisibility(View.GONE);
                    tvEmailError.setText(R.string.error_empty_email);
                    emailLayout.setBackgroundResource(R.drawable.bg_login_field_error);
                    passwordLayout.setBackgroundResource(R.drawable.bg_login_field_normal);
                }else if(passwordField.getText().toString().isEmpty()){
                    tvPasswordError.setVisibility(View.VISIBLE);
                    tvEmailError.setVisibility(View.GONE);
                    tvPasswordError.setText(R.string.error_empty_password);
                    emailLayout.setBackgroundResource(R.drawable.bg_login_field_normal);
                    passwordLayout.setBackgroundResource(R.drawable.bg_login_field_error);
                }else if(!isValidEmail(emailField.getText().toString())){
                    tvEmailError.setVisibility(View.VISIBLE);
                    tvPasswordError.setVisibility(View.GONE);
                    tvEmailError.setText(R.string.validate_email);
                    emailLayout.setBackgroundResource(R.drawable.bg_login_field_error);
                    passwordLayout.setBackgroundResource(R.drawable.bg_login_field_normal);
                }else if((!emailField.getText().toString().equals(username)) || (!passwordField.getText().toString().equals(password))){
                    tvEmailError.setVisibility(View.VISIBLE);
                    tvEmailError.setText(R.string.error_email);
                    tvPasswordError.setVisibility(View.VISIBLE);
                    tvPasswordError.setText(R.string.error_password);
                    emailLayout.setBackgroundResource(R.drawable.bg_login_field_error);
                    passwordLayout.setBackgroundResource(R.drawable.bg_login_field_error);
                }else if((emailField.getText().toString().equals(username)) && (passwordField.getText().toString().equals(password)) && isValidEmail(emailField.getText().toString())){
                    LoginManager.setLoggedIn(getApplicationContext(), true, currentUser);
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
     * Checks if email is valid or not
     * @param target Email String
     * @return bollean value: true if email is valid, false if email is invalid
     */
    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    /**
     * Performs disable or enabled login button behaviour
     */
    private void EnableDisableLogin() {
        emailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().length()!=0){
                    if(!passwordField.getText().toString().isEmpty()){
                        btnLogin.setEnabled(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        passwordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().length()!=0){
                    if(!emailField.getText().toString().isEmpty()){
                        btnLogin.setEnabled(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
