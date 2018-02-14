package com.topcoder.vakidney;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResourcesDetailActivity extends AppCompatActivity {


    private LinearLayout bottomMenu1, bottomMenu2, bottomMenu3, bottomMenu4, bottomMenu5;

    private AppCompatImageView backBtn;

    private String title, desc, actionbartitle;
    private TextView tvTitle, tvDesc, tvActionBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources_detail);
        initView();
        PopulateFields();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(ResourcesDetailActivity.this, MainActivity.class);
                if(actionbartitle.equalsIgnoreCase("Resource Details")) {
                    intent.putExtra("tag", MainActivity.TAG_RESOURCES);
                }else{
                    intent.putExtra("tag", MainActivity.TAG_MEDICATION);
                }
                startActivity(intent);
            }
        });
        initBottomBar();
    }

    /**
     * Populates the Fields
     *
     */
    private void PopulateFields() {
        if(getIntent().hasExtra("actionbartitle")){
            actionbartitle=getIntent().getStringExtra("actionbartitle");
        }
        if (getIntent().hasExtra("title")) {
            title = getIntent().getStringExtra("title");
        }
        if (getIntent().hasExtra("desc")) {
            desc = getIntent().getStringExtra("desc");
        }
        if (title != null) {
            tvTitle.setText(title);
        }
        if (desc != null) {
            tvDesc.setText(desc);
        }
        if(actionbartitle!=null){
            tvActionBarTitle.setText(actionbartitle);
        }
    }

    /**
     * Initializes the view
     */
    private void initView() {
        backBtn = findViewById(R.id.backBtn);
        tvTitle = findViewById(R.id.tvTitle);
        tvDesc = findViewById(R.id.tvDesc);
        tvActionBarTitle=findViewById(R.id.actionBarTitle);
    }

    /**
     * Initialize view and sets up listener for bottom menu
     */
    private void initBottomBar() {
        bottomMenu1 = findViewById(R.id.barLin1);
        bottomMenu2 = findViewById(R.id.barLin2);
        bottomMenu3 = findViewById(R.id.barLin3);
        bottomMenu4 = findViewById(R.id.barLin4);
        bottomMenu5 = findViewById(R.id.barLin5);
        bottomMenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResourcesDetailActivity.this, MainActivity.class);
                intent.putExtra("tag", MainActivity.TAG_HOME);
                startActivity(intent);
                finish();
            }
        });
        bottomMenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResourcesDetailActivity.this, MainActivity.class);
                intent.putExtra("tag", MainActivity.TAG_CHART);
                startActivity(intent);
                finish();
            }
        });
        bottomMenu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResourcesDetailActivity.this, MainActivity.class);
                intent.putExtra("tag", MainActivity.TAG_MEDICATION);
                startActivity(intent);
                finish();
            }
        });
        bottomMenu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResourcesDetailActivity.this, MainActivity.class);
                intent.putExtra("tag", MainActivity.TAG_FOOD);
                startActivity(intent);
                finish();
            }
        });
        bottomMenu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResourcesDetailActivity.this, MainActivity.class);
                intent.putExtra("tag", MainActivity.TAG_WORKOUT);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(ResourcesDetailActivity.this, MainActivity.class);
        if(actionbartitle.equalsIgnoreCase("Resource Details")) {
            intent.putExtra("tag", MainActivity.TAG_RESOURCES);
        }else{
            intent.putExtra("tag", MainActivity.TAG_MEDICATION);
        }
        startActivity(intent);

    }
}
