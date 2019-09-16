package com.example.bms.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bms.R;
import com.example.bms.model.LoginLdap;
import com.example.bms.services.ApiRetrofit;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_singin2;
    private EditText edt_Username, edt_Password;

    //token
    private String sendToken;

    //private static final String EMAIL_ADDRESS = "@mncgroup.com"
    //private static final String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_Username = (EditText) findViewById(R.id.edt_username);
        edt_Password = (EditText) findViewById(R.id.edt_password);

        btn_singin2 = (Button) findViewById(R.id.btn_singin2);

        findViewById(R.id.btn_singin2).setOnClickListener(LoginActivity.this);

//        if (haveNetwork()){
//
//            userLogin();
//
//        } else if (!haveNetwork())
//        {
//            Toast.makeText(LoginActivity.this, "Network connection is not available!", Toast.LENGTH_SHORT).show();
//
//        }
    }

    //method login
    private void userLogin() {

        String username = edt_Username.getText().toString().trim();
        String password = edt_Password.getText().toString().trim();

        if (username.isEmpty()) {
            edt_Username.setError("Username is required");
            edt_Username.requestFocus();
            return;
        }

        /*
        //email
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches)
        {
            edt_Username.setError("Enter a valid email");
            edt_Username.requestFocus();
        }


        //username
        if (!Pattern.compile(USERNAME_PATTERN).matcher(username).matches()) {
            edt_Username.setError("Enter a valid username");
            edt_Username.requestFocus();
        }
        */

        //password
        if (password.isEmpty()) {
            edt_Password.setError("Password is required");
            edt_Password.requestFocus();
        }

        /*
        //validasi
        if (password.length() > 6){
            edt_Password.setError("Password should be atleast 6 character long");
            edt_Password.requestFocus();
        }
        */

        //progressDialog
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        //end progressDialog

        //call class interface and class Apiretrofit
        Call<LoginLdap> call = ApiRetrofit
                .getInstance()
                .getApiClien()
                .UserLdapClient(username, password);


        call.enqueue(new Callback<LoginLdap>() {
            @Override
            public void onResponse(Call<LoginLdap> call, Response<LoginLdap> response) {

                //Log.e("TEST2", response.body().string());

                String token = response.headers().get("Token");
                //String resp = response.body().string();
                //Log.e("AAAA2", resp);
                LoginLdap loginResponse = response.body();


                //if (resp != null) {
                if (token != null) {
                    Log.e("LOGINGAGAL", "x");
                    Log.e("tokenTAG", "Token : " + token);
                    sendToken = token;

                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();


                } else {

                    //Toast.makeText(LoginActivity.this, response.body().string(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("token", response.body().toString());

                    SharedPreferences.Editor sp
                            = getSharedPreferences("TOKEN",
                            MODE_PRIVATE).edit();

                    sp.putString("x", response.body().toString());
                    sp.apply();
                    startActivity(intent);
                    finish();

                }
            }

            @Override
            public void onFailure(Call<LoginLdap> call, Throwable t) {

                if (haveNetwork()) {

                    //userLogin();

                } else if (!haveNetwork()) {

                    Toast.makeText(LoginActivity.this, "Network connection is not available!", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_singin2:
                 userLogin();
                break;
//            case R.id.btn_singUp:
//                userSignUp();
//                break;
        }

    }

    //check conection internet
    private boolean haveNetwork() {
        boolean have_WIFI = false;
        boolean have_MOBILEData = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();

        for (NetworkInfo info : networkInfos) {
            if (info.getTypeName().equalsIgnoreCase("WIFI"))
                if (info.isConnected())
                    have_WIFI = true;

            if (info.getTypeName().equalsIgnoreCase("MOBILE"))
                if (info.isConnected())
                    have_MOBILEData = true;
        }

        return have_MOBILEData || have_WIFI;
    }
}
