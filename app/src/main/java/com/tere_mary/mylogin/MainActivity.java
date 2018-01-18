package com.tere_mary.mylogin;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tere_mary.mylogin._api.iApiService;
import com.tere_mary.mylogin.modul.Users;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL_LOGIN = "https://private-7a2e67-login465.apiary-mock.com/";
    TextView tv_respond, tv_result_api;
    private EditText edEmail, edPassword;
    String strEmail, strPassword, strToken;
    Button in;

    View focusView;
    public Boolean status_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        //koneksi
        if (!konekNet()) {
            Toast.makeText(this, "No connection", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Connection Avaiable", Toast.LENGTH_SHORT).show();
        }

        tv_respond = (TextView) findViewById(R.id.Text_Respond);
        tv_result_api = (TextView) findViewById(R.id.Text_ResultApi);

        edEmail = (EditText) findViewById(R.id.Text_email);
        edPassword = (EditText) findViewById(R.id.Text_password);
        in = (Button) findViewById(R.id.Button_Login);

        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strEmail = String.valueOf(edEmail.getText().toString());
                strPassword = String.valueOf(edPassword.getText().toString());

                attemptLogin();

            }
        });
    }

    //untuk tahu ada koneksi internet atau tidak
    public boolean konekNet() {
        ConnectivityManager konek = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = konek.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    //cek input email comtain @
    private boolean emailValid(String strEmail) {
        return strEmail.contains("@");
    }

    //cek password panjangnya lebih dari 5
    private boolean passwordValid(String strPassword) {
        return strPassword.length() > 5;
    }

    public boolean ValidasiLogin() {
        boolean cancel = false;

        //password kosong atau terlalu pendek
        if (TextUtils.isEmpty(strPassword) && !passwordValid(strPassword)) {
            edPassword.setError(getString(R.string.error_invalid_password));
            focusView = edPassword;
            cancel = true;
        }

        //email kosong
        if (TextUtils.isEmpty(strEmail)) {
            edEmail.setError(getString(R.string.error_email_empty));
            focusView = edEmail;
            cancel = true;

            //kalau emailnya tidak contain @
        } else if (!emailValid(strEmail)) {
            edEmail.setError(getString(R.string.error_invalid_email));
            focusView = edEmail;
            cancel = true;
        }
        return cancel;
    }

    private void cek_login(final String strEmail, final String strPassword) {
        //nyambung ke gson-nya
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'H:mm:ssZ")
                .create();

        //nyambung ke retrofit-nya
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_LOGIN)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        //ke interface api-nya
        iApiService login_api = retrofit.create(iApiService.class);
        Call<Users> call = login_api.getUsers();
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                int status = response.code();
                tv_respond.setText("Nilai Response API = " + String.valueOf(status));
                for (Users.UserItem user : response.body().getUsers()) {
                    status_login = false;

                    if (strEmail.equals(user.getEmail()) && strPassword.equals(user.getPassword())) {
                        status_login = true;

                        Toast.makeText(MainActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, TwoActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    } else {
                        Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                tv_respond.setText("Nilai Response API = " + String.valueOf(t));
                Log.d("Login", "Login Response-" + t.getMessage());
            }
        });
    }

    public void attemptLogin() {
        boolean mCancel = this.ValidasiLogin();

        if (mCancel) {
            focusView.requestFocus();
        } else {
            cek_login(strEmail, strPassword);
        }
    }
}
