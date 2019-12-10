package com.startergate.thundercloudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    Button signup, login;
    EditText id_enter, pw_enter;

    String clientid = "UAiiUAl5GLyVwNoBFUYwtD2uJPHa4gNVWelx0H2AIGiW5zuzZ5lDCK02e6hcRX5e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signup = findViewById(R.id.signup);
        login = findViewById(R.id.login);
        id_enter = findViewById(R.id.id_enter);
        pw_enter = findViewById(R.id.pw_enter);

        final RequestQueue queue = Volley.newRequestQueue(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://sid.donote.co:3000/user/login")));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id_enter.getText().toString().equals("")) {
                    Toast.makeText(sup, "ID를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String url ="http://donote.co:8000/api/session/";

                HashMap<String, String> data = new HashMap<>();
                data.put("clientid", clientid);
                data.put("id", id_enter.getText().toString());
                data.put("pw", pw_enter.getText().toString());

                // Request a string response from the provided URL.
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(data),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.e("response", response.toString());
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // failed
                    }
                });

                // Add the request to the RequestQueue.
                queue.add(request);
                // startActivity(new Intent()); // whatever. fuck
            }
        });
    }
}
