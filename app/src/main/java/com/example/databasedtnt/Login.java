package com.example.databasedtnt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard); // Replace with your login layout if necessary

        EditText txt_uname = findViewById(R.id.txt_uname);
        EditText txt_pword = findViewById(R.id.txt_pword);
        Button btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = txt_uname.getText().toString().trim();
                String pword = txt_pword.getText().toString().trim();

                if (uname.isEmpty() || pword.isEmpty()) {
                    Toast.makeText(Login.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://30.0.0.122/Testing_php/login.php"; // Your PHP endpoint

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Login Response", response);  // Log the full response for debugging
                                if (response.trim().equalsIgnoreCase("Login successful")) {
                                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    // Redirect to the next activity, e.g., Main screen
                                    Intent intent = new Intent(Login.this, Second.class);
                                    startActivity(intent);
                                    finish(); // optional: prevents going back to this login screen
                                } else {
                                    Toast.makeText(Login.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Login Error", error.toString());  // Log error response for debugging
                                Toast.makeText(Login.this, "Volley Error: " + error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("uname", uname);
                        params.put("pword", pword);
                        return params;
                    }
                };

                queue.add(stringRequest);
            }
        });
    }
}

