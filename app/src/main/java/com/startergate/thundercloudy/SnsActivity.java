package com.startergate.thundercloudy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

public class SnsActivity extends AppCompatActivity {

    ImageButton btn_main, btn_add, btn_cloud;

    TextView user_welcome;

    SidData sidData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sns);

        sidData = new SidData();
        sidData.sessid = getIntent().getStringExtra("sessid");
        sidData.pid = getIntent().getStringExtra("pid");
        sidData.nickname = getIntent().getStringExtra("nickname");

        btn_main = findViewById(R.id.btn_main);
        btn_add = findViewById(R.id.btn_add);
        btn_cloud = findViewById(R.id.btn_cloud);
        user_welcome = findViewById(R.id.user_welcome);

        user_welcome.setText(user_welcome.getText().toString().replace("{USERNAME}", sidData.nickname));

        btn_main.setBackgroundResource(R.drawable.border_activated);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SnsActivity.this, PostActivity.class);
                intent.putExtra("sessid", sidData.sessid);
                intent.putExtra("pid", sidData.pid);
                intent.putExtra("nickname", sidData.nickname);

                startActivity(intent);
                SnsActivity.this.overridePendingTransition(0, 0);
                SnsActivity.this.finish();
            }
        });

        btn_cloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SnsActivity.this, CloudActivity.class);
                intent.putExtra("sessid", sidData.sessid);
                intent.putExtra("pid", sidData.pid);
                intent.putExtra("nickname", sidData.nickname);
                startActivity(intent);
                SnsActivity.this.overridePendingTransition(0, 0);
                SnsActivity.this.finish();
            }
        });
    }
}
