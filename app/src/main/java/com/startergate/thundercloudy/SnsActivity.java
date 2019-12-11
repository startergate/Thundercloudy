package com.startergate.thundercloudy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SnsActivity extends AppCompatActivity {

    ImageButton btn_main, btn_add, btn_cloud;

    TextView user_welcome;

    LinearLayout content_view;

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

        content_view = findViewById(R.id.content_view);

        final RequestQueue queue = Volley.newRequestQueue(this);

        user_welcome.setText(user_welcome.getText().toString().replace("{USERNAME}", sidData.nickname));

        btn_main.setBackgroundResource(R.drawable.border_activated);
        try {
            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "http://donote.co:8000/api/post/all/", new JSONObject("{}"),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.d("sdfsdf", response.toString());
                                JSONArray array = response.getJSONArray("response");
                                LayoutInflater inflater = LayoutInflater.from(SnsActivity.this);
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject obj = array.getJSONObject(i);
                                    LinearLayout linear = (LinearLayout) inflater.inflate(R.layout.inflated, null, false);
                                    TextView tv_user = new TextView(getApplicationContext());
                                    tv_user.setText(obj.getString("user"));
                                    TextView tv_title = new TextView(getApplicationContext());
                                    tv_title.setText(obj.getString("title"));
                                    TextView tv_content = new TextView(getApplicationContext());
                                    tv_content.setText(obj.getString("content"));
                                    linear.addView(tv_user);
                                    linear.addView(tv_title);
                                    linear.addView(tv_content);

                                    content_view.addView(linear);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Server Not Available.", Toast.LENGTH_SHORT).show();
                    Log.e("failed", error.toString());
                }
            });

            // Add the request to the RequestQueue.
            queue.add(request);
            // startActivity(new Intent()); // whatever. fuck
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
