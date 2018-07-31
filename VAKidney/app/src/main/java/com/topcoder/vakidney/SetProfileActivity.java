package com.topcoder.vakidney;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.topcoder.vakidney.databinding.ActivitySetProfileBinding;
import com.topcoder.vakidney.model.UserData;
import com.topcoder.vakidney.util.GoalGenerator;

public class SetProfileActivity extends AppCompatActivity {

    ActivitySetProfileBinding binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = DataBindingUtil.setContentView(this, R.layout.activity_set_profile);

        Typeface boldTypeface = ResourcesCompat.getFont(this, R.font.nexa_bold);
        binder.actionBarTitle.setTypeface(boldTypeface);

        binder.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserData userData = UserData.get();

                if (userData == null || userData.getFullname() == null ||
                        userData.getAge() == 0 ||
                        userData.getHeightFeet() == 0 ||
                        userData.getWeight() == 0 ) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SetProfileActivity.this);
                    builder.setMessage("Please fill all required items. (Name, age, height, weight)")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    final AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else {
                    navigateToHomeScreen();
                }

            }
        });
    }

    /**
     * Navigate to Home Screen
     */
    private void navigateToHomeScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}
