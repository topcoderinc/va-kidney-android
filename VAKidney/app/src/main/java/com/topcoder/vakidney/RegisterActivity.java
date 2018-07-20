package com.topcoder.vakidney;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;

import com.topcoder.vakidney.databinding.ActivityLoginBinding;
import com.topcoder.vakidney.databinding.ActivityRegisterBinding;
import com.topcoder.vakidney.model.UserData;
import com.topcoder.vakidney.util.DialogManager;
import com.topcoder.vakidney.util.JsondataUtil;
import com.topcoder.vakidney.util.LoginManager;

/**
 * This class is used to perform logging in task
 */
public class RegisterActivity extends AppCompatActivity {

    private String username;
    private String password;
    private UserData currentUser;

    ActivityRegisterBinding binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_register);
        currentUser = JsondataUtil.getUserData(getApplicationContext());
        username = currentUser.getUsername();
        password = currentUser.getPassword();
        binder.btnLogin.setEnabled(false);
        binder.emailField.clearFocus();

        EnableDisableLogin();

        binder.emailField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    binder.emailErrorTv.setVisibility(View.GONE);
                    binder.emailLayout.setBackgroundResource(R.drawable.bg_login_field_normal);
                }
            }
        });

        binder.passwordField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    binder.passwordErrorTv.setVisibility(View.GONE);
                    binder.passwordLayout.setBackgroundResource(R.drawable.bg_login_field_normal);
                }
            }
        });

        binder.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binder.emailField.getText().toString().isEmpty()) {
                    binder.emailErrorTv.setVisibility(View.VISIBLE);
                    binder.passwordErrorTv.setVisibility(View.GONE);
                    binder.emailErrorTv.setText(R.string.error_empty_email);
                    binder.emailLayout.setBackgroundResource(R.drawable.bg_login_field_error);
                    binder.passwordLayout.setBackgroundResource(R.drawable.bg_login_field_normal);
                } else if (binder.passwordField.getText().toString().isEmpty()) {
                    binder.passwordErrorTv.setVisibility(View.VISIBLE);
                    binder.emailErrorTv.setVisibility(View.GONE);
                    binder.passwordErrorTv.setText(R.string.error_empty_password);
                    binder.emailLayout.setBackgroundResource(R.drawable.bg_login_field_normal);
                    binder.passwordLayout.setBackgroundResource(R.drawable.bg_login_field_error);
                } else if (!isValidEmail(binder.emailField.getText().toString())) {
                    binder.emailErrorTv.setVisibility(View.VISIBLE);
                    binder.passwordErrorTv.setVisibility(View.GONE);
                    binder.emailErrorTv.setText(R.string.validate_email);
                    binder.emailLayout.setBackgroundResource(R.drawable.bg_login_field_error);
                    binder.passwordLayout.setBackgroundResource(R.drawable.bg_login_field_normal);
                } else if ((!binder.emailField.getText().toString().equals(username)) || (!binder.passwordField.getText().toString().equals(password))) {
                    binder.emailErrorTv.setVisibility(View.VISIBLE);
                    binder.emailErrorTv.setText(R.string.error_email);
                    binder.passwordErrorTv.setVisibility(View.VISIBLE);
                    binder.passwordErrorTv.setText(R.string.error_password);
                    binder.emailLayout.setBackgroundResource(R.drawable.bg_login_field_error);
                    binder.passwordLayout.setBackgroundResource(R.drawable.bg_login_field_error);
                } else if ((binder.emailField.getText().toString().equals(username)) && (binder.passwordField.getText().toString().equals(password)) && isValidEmail(binder.emailField.getText().toString())) {
                    LoginManager.setLoggedIn(getApplicationContext(), true, currentUser);
                    NavigateHome();
                }
            }
        });
        binder.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.showOkDialog(RegisterActivity.this, getString(R.string.feature_not_implemented), null);
            }
        });
        binder.forgotPasswordTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.showOkDialog(RegisterActivity.this, getString(R.string.feature_not_implemented), null);
            }
        });
    }

    /**
     * Checks if email is valid or not
     *
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
        binder.emailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() != 0) {
                    if (!binder.passwordField.getText().toString().isEmpty()) {
                        binder.btnLogin.setEnabled(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binder.passwordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() != 0) {
                    if (!binder.emailField.getText().toString().isEmpty()) {
                        binder.btnLogin.setEnabled(true);
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
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}
