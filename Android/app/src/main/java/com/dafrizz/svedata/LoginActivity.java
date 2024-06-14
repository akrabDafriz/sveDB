package com.dafrizz.svedata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dafrizz.svedata.model.BaseResponse;
import com.dafrizz.svedata.model.User;
import com.dafrizz.svedata.request.BaseAPIService;
import com.dafrizz.svedata.request.UtilsAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private Context mContext;
    private BaseAPIService mApiService;
    public static User loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        mApiService = UtilsAPI.getApiService();
        TextView registerNow = findViewById(R.id.login_register);
        Button loginButton = findViewById(R.id.loginButton);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);

        registerNow.setOnClickListener(v->{
            moveActivity(this, RegisterActivity.class);
        });


        loginButton.setOnClickListener(v-> {
            handleLogin();
        });
    }

    protected void handleLogin() {
        System.out.println("Handle Login WOOOOOY");
        String emailS = email.getText().toString();
        String passwordS = password.getText().toString();
        System.out.println(""+emailS+" "+passwordS);
        if (emailS.isEmpty() || passwordS.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        System.out.println("Initiating network call");
        mApiService.login(emailS, passwordS).enqueue(new Callback<BaseResponse<User>>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                if (!response.isSuccessful()) {
                    System.out.println(response);
                    System.out.println("GAGAL WOY");
                    Toast.makeText(mContext, "Zie Application error " + response.code()+" "+ response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<User> res = response.body();
                if (res!=null) {
                    System.out.println("sukses harusnya");
                    loggedUser = res.payload;
                    moveActivity(mContext, MainActivity.class);
                    email.setText("");
                    password.setText("");
                    viewToast(mContext, "Berhasil Login");
                }
                System.out.println("bawahnya sukses");
            }

            @Override
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
                System.out.println(t);
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    private void viewToast(Context ctx, String message){
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }
}