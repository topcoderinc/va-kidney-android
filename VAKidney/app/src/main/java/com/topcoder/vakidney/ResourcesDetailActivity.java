package com.topcoder.vakidney;

import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.topcoder.vakidney.databinding.ActivityResourcesDetailBinding;

public class ResourcesDetailActivity extends AppCompatActivity {


    private String title, desc, url, actionbartitle;
    ActivityResourcesDetailBinding binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binder = DataBindingUtil.setContentView(this, R.layout.activity_resources_detail);

        PopulateFields();
        binder.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Typeface boldTypeface = ResourcesCompat.getFont(this, R.font.nexa_bold);
        binder.actionBarTitle.setTypeface(boldTypeface);
        binder.tvTitle.setTypeface(boldTypeface);
    }

    /**
     * Populates the Fields
     */
    private void PopulateFields() {
        if (getIntent().hasExtra("actionbartitle")) {
            actionbartitle = getIntent().getStringExtra("actionbartitle");
        }
        if (getIntent().hasExtra("title")) {
            title = getIntent().getStringExtra("title");
        }
        if (getIntent().hasExtra("desc")) {
            desc = getIntent().getStringExtra("desc");
        }
        if (getIntent().hasExtra("url")) {
            url = getIntent().getStringExtra("url");
        }
        if (title != null) {
            binder.tvTitle.setText(title);
        }
        if (desc != null) {
            binder.tvDesc.setText(desc);
        }
        if (url != null) {
            binder.tvDesc.setText(binder.tvDesc.getText() + "\n\n" + url);
        }
        if (actionbartitle != null) {
            binder.actionBarTitle.setText(actionbartitle);
        }
    }
}
