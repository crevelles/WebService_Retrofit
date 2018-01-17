package com.example.cristobal.webservice_retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofitUtils.APIRestService;

public class MainActivity extends AppCompatActivity {

    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb = findViewById(R.id.IDprogressBar);
        pb.setVisibility(View.INVISIBLE);
    }

    public void consumirWS(View v) {

        Retrofit r = RetrofitClient.getClient(APIRestService.BASE_URL);
        APIRestService ars = r.create(APIRestService.class);
        Call<String> call = ars.obtenerPokemon();

        // hacemos visible el progress bar que habremos inicializado en el
        // onCreate poniéndolo como oculto
        pb.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<String>() {

            String res;
            TextView tvRes = (TextView) findViewById(R.id.IDtexto);

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                pb.setVisibility(View.GONE); // lo escondemos
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Error" + response.code(), Toast.LENGTH_LONG);
                    //Log.i(tag:, "Error" + response.code());
                } else {
                    res = response.body().toString();
                    tvRes.setText(res);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                pb.setVisibility(View.GONE); // lo escondemos
                Log.e("error", t.toString());
            }
        });

    }
}