package com.dafrizz.svedata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dafrizz.svedata.model.BaseResponse;
import com.dafrizz.svedata.model.User;
import com.dafrizz.svedata.request.BaseAPIService;
import com.dafrizz.svedata.request.UtilsAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    private Context mContext;
    private BaseAPIService mApiService;
    private EditText newUsername;
    private EditText newEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mContext = this;
        mApiService = UtilsAPI.getApiService();
        Button submitEdit = findViewById(R.id.submit_edit_button);
        newUsername = findViewById(R.id.new_username);
        newEmail = findViewById(R.id.new_email);

        submitEdit.setOnClickListener(v -> {
            String newUsernameS = newUsername.getText().toString();
            String newEmailS = newEmail.getText().toString();
            String userId = LoginActivity.loggedUser.id;

            System.out.println(newUsernameS);
            System.out.println(newEmailS);
            handleSubmit(userId, newUsernameS, newEmailS);
        });
    }
    private void handleSubmit(String userId, String newUsernameS, String newEmailS){
        mApiService.updateUser(userId, newUsernameS, newEmailS).enqueue(new Callback<BaseResponse<User>>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                if (!response.isSuccessful()) {
                    runOnUiThread(() -> Toast.makeText(mContext, "Error " + response.code() + " " + response.message(), Toast.LENGTH_SHORT).show());
                    return;
                }
                BaseResponse<User> res = response.body();
                if (res.success) {
                    LoginActivity.loggedUser = res.payload;
                    newEmail.setText("");
                    newUsername.setText("");
                    viewToast(mContext, "Berhasil update");
                    moveActivity(mContext, MainActivity.class);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
                runOnUiThread(() -> Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show());
            }
        });
    }
    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    private void viewToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }
}
