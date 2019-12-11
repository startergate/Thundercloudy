package com.startergate.thundercloudy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity {

    ImageButton btn_main, btn_add, btn_cloud;

    EditText title, content;

    Button select_image, send;

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

        select_image = findViewById(R.id.select_image);
        send = findViewById(R.id.send);

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);

        btn_add.setBackgroundResource(R.drawable.border_activated);
        final RequestQueue queue = Volley.newRequestQueue(this);

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

        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, FileSelectActivity.class);
                intent.putExtra("pid", sidData.pid);

                startActivityForResult(intent, 0);

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url ="http://donote.co:8000/api/post/";

                String data = "";

                // Request a string response from the provided URL.
                StringRequest request = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Log.e("response", response.toString());
                                Intent intent = new Intent(PostActivity.this, SnsActivity.class);
                                intent.putExtra("sessid", sidData.sessid);
                                intent.putExtra("pid", sidData.pid);
                                intent.putExtra("nickname", sidData.nickname);
                                startActivity(intent);
                                PostActivity.this.finish();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Server Not Available.", Toast.LENGTH_SHORT).show();
                        Log.e("failed", error.toString());
                    }
                }) {
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("user",sidData.nickname);
                        params.put("title",title.getText().toString());
                        params.put("content", content.getText().toString());

                        return params;
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/x-www-form-urlencoded; charset=UTF-8";
                    }
                };

                // Add the request to the RequestQueue.
                queue.add(request);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            assert data != null;
            data.getStringExtra("image_name");
        } catch (Exception ignored) {
            Toast.makeText(getApplicationContext(), "파일 업로드 에러", Toast.LENGTH_SHORT).show();
        }
    }
}
