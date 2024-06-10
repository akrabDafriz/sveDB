package com.dafrizz.svedata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.dafrizz.svedata.model.BaseResponse;
import com.dafrizz.svedata.model.DeckList;
import com.dafrizz.svedata.model.ListList;
import com.dafrizz.svedata.request.BaseAPIService;
import com.dafrizz.svedata.request.UtilsAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllListActivity extends AppCompatActivity {
    private BaseAPIService mApiService;
    private Context mContext;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> listNames;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_list);

        mContext = this;
        mApiService = UtilsAPI.getApiService();

        handleList();
    }
    protected void handleList() {
        listView = findViewById(R.id.list_list_view);
        listNames = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listNames);
        listView.setAdapter(adapter);

        mApiService.getAllLists().enqueue(new Callback<BaseResponse<List<ListList>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<ListList>>> call, Response<BaseResponse<List<ListList>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BaseResponse<List<ListList>> res = response.body();
                    System.out.println("response is not null WOOOO");
                    System.out.println(res);
                    List<ListList> lists = res.payload;
                    if (lists != null) {
                        for (ListList list : lists) {
                            listNames.add(list.list_name);
                        }
                        adapter.notifyDataSetChanged();
                    }
                    System.out.println(lists);
                } else {
                    Toast.makeText(mContext, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<ListList>>> call, Throwable t) {
                // Handle the failure
                Toast.makeText(mContext, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}