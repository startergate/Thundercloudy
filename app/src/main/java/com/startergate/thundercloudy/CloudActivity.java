package com.startergate.thundercloudy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CloudActivity extends AppCompatActivity {

    ImageButton btn_main, btn_add, btn_cloud;

    SidData sidData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud);

        sidData = new SidData();
        sidData.sessid = getIntent().getStringExtra("sessid");
        sidData.pid = getIntent().getStringExtra("pid");
        sidData.nickname = getIntent().getStringExtra("nickname");

        btn_main = findViewById(R.id.btn_main);
        btn_add = findViewById(R.id.btn_add);
        btn_cloud = findViewById(R.id.btn_cloud);

        btn_cloud.setBackgroundResource(R.drawable.border_activated);

        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CloudActivity.this, SnsActivity.class);
                intent.putExtra("sessid", sidData.sessid);
                intent.putExtra("pid", sidData.pid);
                intent.putExtra("nickname", sidData.nickname);

                startActivity(intent);
                CloudActivity.this.overridePendingTransition(0, 0);
                CloudActivity.this.finish();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CloudActivity.this, PostActivity.class);
                intent.putExtra("sessid", sidData.sessid);
                intent.putExtra("pid", sidData.pid);
                intent.putExtra("nickname", sidData.nickname);

                startActivity(intent);
                CloudActivity.this.overridePendingTransition(0, 0);
                CloudActivity.this.finish();
            }
        });
    }
}
