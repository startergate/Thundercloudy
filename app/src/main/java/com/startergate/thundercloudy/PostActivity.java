package com.startergate.thundercloudy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PostActivity extends AppCompatActivity {

    ImageButton btn_main, btn_add, btn_cloud;

    SidData sidData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        sidData = new SidData();
        sidData.sessid = getIntent().getStringExtra("sessid");
        sidData.pid = getIntent().getStringExtra("pid");
        sidData.nickname = getIntent().getStringExtra("nickname");

        btn_main = findViewById(R.id.btn_main);
        btn_add = findViewById(R.id.btn_add);
        btn_cloud = findViewById(R.id.btn_cloud);

        btn_add.setBackgroundResource(R.drawable.border_activated);

        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, SnsActivity.class);
                intent.putExtra("sessid", sidData.sessid);
                intent.putExtra("pid", sidData.pid);
                intent.putExtra("nickname", sidData.nickname);

                startActivity(intent);
                PostActivity.this.overridePendingTransition(0, 0);
                PostActivity.this.finish();
            }
        });

        btn_cloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, CloudActivity.class);
                intent.putExtra("sessid", sidData.sessid);
                intent.putExtra("pid", sidData.pid);
                intent.putExtra("nickname", sidData.nickname);

                startActivity(intent);
                PostActivity.this.overridePendingTransition(0, 0);
                PostActivity.this.finish();
            }
        });
    }
}
