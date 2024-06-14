package com.dafrizz.svedata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dafrizz.svedata.request.BaseAPIService;
import com.dafrizz.svedata.request.UtilsAPI;

public class ProfileActivity extends AppCompatActivity {
    private Context mContext;
    private BaseAPIService mApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mContext = this;
        mApiService = UtilsAPI.getApiService();

        TextView username = findViewById(R.id.acc_username);
        TextView email = findViewById(R.id.acc_email);
        username.setText(LoginActivity.loggedUser.username);
        email.setText(LoginActivity.loggedUser.email);

        Button editProfile = findViewById(R.id.submit_edit_button);
        editProfile.setOnClickListener(v->moveActivity(mContext, EditProfileActivity.class));


        String id = LoginActivity.loggedUser.id;


    }
    private void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    private void viewToast(Context ctx, String message){
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }
}