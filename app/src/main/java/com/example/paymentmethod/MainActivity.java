package com.example.paymentmethod;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paymentmethod.adapters.PaymentListRecyclerViewAdapter;
import com.example.paymentmethod.apis.ApiCallService;
import com.example.paymentmethod.apis.ApiInterface;
import com.example.paymentmethod.models.ListResult;
import com.example.paymentmethod.utils.InfoDialog;
import com.github.ybq.android.spinkit.SpinKitView;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    private InfoDialog mInfoDialog;
    private static final String BASE_URL  = "https://raw.githubusercontent.com/optile/checkout-android/develop/shared-test/lists/";
    private SpinKitView mSpinKitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSpinKitView = findViewById(R.id.id_spin_kit);

        mInfoDialog = new InfoDialog(this);
        if (isNetworkAvailable()) {
            try {
                getData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mInfoDialog.showDialog(true, "");
        }
    }

    private void getData() throws IOException{
        ApiCallService apiCallService = new ApiCallService(BASE_URL);
        ApiInterface apiInterface = apiCallService.buildService(ApiInterface.class);
        Call<ListResult> dataCall = apiInterface.getListData();
        dataCall.enqueue(new Callback<ListResult>() {
            @Override
            public void onResponse(Call<ListResult> call, Response<ListResult> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mSpinKitView.setVisibility(View.GONE);
                        configureRecyclerView(response.body());
                    }
                } else {
                    mInfoDialog.showDialog(false, String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<ListResult> call, Throwable t) {
                mInfoDialog.showDialog(false, t.getMessage());
            }
        });
    }

    private void configureRecyclerView(ListResult listResult) {
        RecyclerView recyclerView = findViewById(R.id.id_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        PaymentListRecyclerViewAdapter paymentListRecyclerViewAdapter = new PaymentListRecyclerViewAdapter(getApplicationContext(), listResult);
        recyclerView.setAdapter(paymentListRecyclerViewAdapter);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}