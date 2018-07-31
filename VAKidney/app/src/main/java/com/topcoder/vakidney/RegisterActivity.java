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
import android.widget.Toast;

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


    private final static int PASSWORD_MINIMUM_CHARACTER = 6;
    ActivityRegisterBinding binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binder.emailField.clearFocus();

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

        binder.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binder.emailField.getText().toString().isEmpty()) {
                    binder.emailErrorTv.setVisibility(View.VISIBLE);
                    binder.emailErrorTv.setText(R.string.error_empty_email);
                    binder.emailLayout.setBackgroundResource(R.drawable.bg_login_field_error);

                    binder.passwordLayout.setBackgroundResource(R.drawable.bg_login_field_normal);
                    binder.passwordErrorTv.setVisibility(View.GONE);

                    binder.confirmErrorTv.setVisibility(View.GONE);
                    binder.confirmLayout.setBackgroundResource(R.drawable.bg_login_field_normal);

                }
                else if (binder.passwordField.getText().toString().isEmpty()) {
                    binder.passwordErrorTv.setVisibility(View.VISIBLE);
                    binder.passwordErrorTv.setText(R.string.error_empty_password);
                    binder.passwordLayout.setBackgroundResource(R.drawable.bg_login_field_error);

                    binder.emailErrorTv.setVisibility(View.GONE);
                    binder.emailLayout.setBackgroundResource(R.drawable.bg_login_field_normal);

                    binder.confirmErrorTv.setVisibility(View.GONE);
                    binder.confirmLayout.setBackgroundResource(R.drawable.bg_login_field_normal);

                }
                else if (binder.passwordField.getText().toString().length() < PASSWORD_MINIMUM_CHARACTER) {
                    binder.passwordErrorTv.setVisibility(View.VISIBLE);
                    binder.passwordErrorTv.setText(
                            String.format(
                                    getString(R.string.error_password_min),
                                    PASSWORD_MINIMUM_CHARACTER
                            )
                    );
                    binder.passwordLayout.setBackgroundResource(R.drawable.bg_login_field_error);

                    binder.emailErrorTv.setVisibility(View.GONE);
                    binder.emailLayout.setBackgroundResource(R.drawable.bg_login_field_normal);

                    binder.confirmErrorTv.setVisibility(View.GONE);
                    binder.confirmLayout.setBackgroundResource(R.drawable.bg_login_field_normal);

                }
                else if (binder.confirmField.getText().toString().isEmpty()) {
                    binder.confirmErrorTv.setVisibility(View.VISIBLE);
                    binder.confirmErrorTv.setText(R.string.error_empty_password);
                    binder.confirmLayout.setBackgroundResource(R.drawable.bg_login_field_error);

                    binder.emailErrorTv.setVisibility(View.GONE);
                    binder.emailLayout.setBackgroundResource(R.drawable.bg_login_field_normal);

                    binder.passwordErrorTv.setVisibility(View.GONE);
                    binder.passwordLayout.setBackgroundResource(R.drawable.bg_login_field_normal);

                }
                else if (!isValidEmail(binder.emailField.getText().toString())) {
                    binder.emailErrorTv.setVisibility(View.VISIBLE);
                    binder.passwordErrorTv.setVisibility(View.GONE);
                    binder.emailErrorTv.setText(R.string.validate_email);
                    binder.emailLayout.setBackgroundResource(R.drawable.bg_login_field_error);
                    binder.passwordLayout.setBackgroundResource(R.drawable.bg_login_field_normal);

                    binder.confirmErrorTv.setVisibility(View.GONE);
                    binder.confirmLayout.setBackgroundResource(R.drawable.bg_login_field_normal);

                }
                else if (binder.passwordField.getText().toString().compareTo(
                                binder.confirmField.getText().toString()) != 0) {
                    binder.confirmErrorTv.setVisibility(View.VISIBLE);
                    binder.confirmErrorTv.setText(R.string.error_password_not_match);
                    binder.confirmLayout.setBackgroundResource(R.drawable.bg_login_field_error);

                    binder.emailErrorTv.setVisibility(View.GONE);
                    binder.emailLayout.setBackgroundResource(R.drawable.bg_login_field_normal);

                    binder.passwordErrorTv.setVisibility(View.GONE);
                    binder.passwordLayout.setBackgroundResource(R.drawable.bg_login_field_normal);
                }
                else {
                    String username = binder.emailField.getText().toString();
                    String password = binder.passwordField.getText().toString();
                    if (UserData.get(username) == null) {
                        UserData user = JsondataUtil.getUserData(getApplicationContext());
                        user.setUsername(username);
                        user.setPassword(password);
                        LoginManager.setLoggedIn(getApplicationContext(), true, user);
                        navigateSetProfile();
                    }
                    else {
                        Toast.makeText(
                                RegisterActivity.this,
                                "Your email is already registered",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        binder.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.this.finish();
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
     * Navigate to Set Profile Screen
     */
    private void navigateSetProfile() {
        Intent intent = new Intent(RegisterActivity.this, SetProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}
