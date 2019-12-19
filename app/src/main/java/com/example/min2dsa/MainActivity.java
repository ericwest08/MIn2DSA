package com.example.min2dsa;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.internal.EverythingIsNonNull;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.min2dsa.models.Museums;

public class MainActivity extends AppCompatActivity {
    private API api;
    private Museums myMuseums;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    private RecyclerView recyclerView;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://do.diba.cat/api/dataset/museus/format/json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(API.class);

        Toast toast = Toast.makeText(getApplicationContext(),
                "Showing museums",
                Toast.LENGTH_SHORT);
        toast.show();

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        getMuseums();
    }

        public void getMuseums(){
            Call<Museums> call = api.getMuseums(1,11);

            call.enqueue(new Callback<Museums>(){
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<Museums> call, Response<Museums> response) {
                    if(!response.isSuccessful())
                    {
                        progressBar.setVisibility(View.GONE);
                        dialogMessage("Failed of the list request");
                    }

                    myMuseums = response.body();

                    mAdapter = new Adapter(myMuseums.getElements());
                    recyclerView.setAdapter(mAdapter);

                    progressBar.setVisibility(View.GONE);
                }
                @EverythingIsNonNull
                @Override
                public void onFailure(Call<Museums> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    dialogMessage("Failed to get the data");
                }
            });
        }

        public void dialogMessage(String result) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog dialog = builder.create();
            builder.setMessage(result).setTitle("Info");
            Dialog dialeg = builder.create();
            dialeg.show();
        }

    }

