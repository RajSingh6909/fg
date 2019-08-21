package com.example.foodgospel.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodgospel.Global.Config;
import com.example.foodgospel.Global.ConnectivityReceiver;
import com.example.foodgospel.Global.FoodGospelSharedPrefernce;
import com.example.foodgospel.Global.GlobalHandler;
import com.example.foodgospel.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    EditText etUserName, etPassword;
    Button btnLogin;
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String StrUserName, StrPassword, strUserID, strName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = getApplicationContext();

        sharedPreferences = getApplicationContext().getSharedPreferences("loginPref", MODE_PRIVATE);

        etUserName = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectivityReceiver.checkconnection(getApplicationContext())) {
                    checkAllFields();

                } else {
                    GlobalHandler.showconnectiondialog(getApplicationContext(), null);
                }
            }
        });

    }

    private boolean checkAllFields() {
        boolean flag = true;
        Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        if (etUserName.getText().toString().isEmpty()) {
            etUserName.setError("Username cannot be empty.");
            etUserName.startAnimation(shake);
            flag = false;
        }
        if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("Password cannot be empty.");
            etPassword.startAnimation(shake);
            flag = false;
        }

        Login();

        return flag;

    }

    public void Login() {

        StrUserName = etUserName.getText().toString();
        StrPassword = etPassword.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String Message = null;
                if (response.contains("true")) {
                    try {
                        JSONObject object = new JSONObject(response);
                        Message = object.getString("message");
                        strUserID = object.getString("id");
                        strName = object.getString("name");
                        Toast.makeText(getApplicationContext(), Message, Toast.LENGTH_LONG).show();
                        FoodGospelSharedPrefernce.setSharePrefernceData(LoginActivity.this, "USERID", strUserID);
                        FoodGospelSharedPrefernce.setSharePrefernceData(LoginActivity.this, "NAME", strName);
                       /* editor = sharedPreferences.edit();
                        editor.putString("UserId", strUserID);
                        editor.putString("Name", strName);
                        editor.clear();
                        editor.apply();*/

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("value", StrUserName);
                        editor.apply();

                        Intent i = new Intent(LoginActivity.this, NavHomeActivity.class);
                        startActivity(i);
                        finish();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Please Check your credentials!", Toast.LENGTH_LONG).show();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "OnError PostResponse" + error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Config.DEFAULTEMAILNAME, Config.DEFAULTHEADEREMAIL);
                params.put(Config.DEFAULTPASSWORD, Config.DEFAULTHEADERPASSWORD);
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Config.DEFAULTEMAILNAME, StrUserName);
                params.put(Config.DEFAULTPASSWORD, StrPassword);
                return params;

            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

}
