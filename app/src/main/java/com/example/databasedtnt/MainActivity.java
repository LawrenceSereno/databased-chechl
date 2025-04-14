package com.example.databasedtnt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText txt_uname = findViewById(R.id.txt_uname);
        EditText txt_pword = findViewById(R.id.txt_pword);
        EditText txt_email = findViewById(R.id.txt_email);
        Button btn_save = findViewById(R.id.btn_save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = txt_uname.getText().toString().trim();
                String pword = txt_pword.getText().toString().trim();
                String email = txt_email.getText().toString().trim();

                if (uname.isEmpty() || pword.isEmpty() || email.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://30.0.0.122/Testing_php/create.php"; // Your local PHP endpoint

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String trimmedResponse = response.trim();
                                if (trimmedResponse.equalsIgnoreCase("User added successfully!")) {
                                    Toast.makeText(MainActivity.this, "Data Added", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, Login.class);
                                    startActivity(intent);
                                    finish(); // Optional: to prevent going back
                                } else if (trimmedResponse.equalsIgnoreCase("User already exists")) {
                                    Toast.makeText(MainActivity.this, "User already exists", Toast.LENGTH_LONG).show();
                                } else if (trimmedResponse.equalsIgnoreCase("Field Required")) {
                                    Toast.makeText(MainActivity.this, "Please complete all fields", Toast.LENGTH_LONG).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, "Volley Error: " + error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("uname", uname);
                        params.put("pword", pword);
                        params.put("email", email);
                        return params;
                    }
                };

                queue.add(stringRequest);
            }
        });
    }
}
