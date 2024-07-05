package com.example.getrequesturl;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private TextView textViewAge;
    private EditText editText;
    private Button btnJson;
    private Button btnAge;

    private Retrofit retrofit;
    private AgifyApi agifyApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textViewResult);
        textViewAge = findViewById(R.id.textViewAge);
        editText = findViewById(R.id.textName);
        btnJson = findViewById(R.id.buttonValidate);;
        btnAge = findViewById(R.id.buttonAge);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.agify.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        agifyApi = retrofit.create(AgifyApi.class);

        btnJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkName(editText.getText().toString());
            }
        });

        btnAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAgeFromJSON(textView.getText().toString());
            }
        });
    }

    private void checkName(String name) {
        Call<AgeResponse> call = agifyApi.getAge(name);
        call.enqueue(new Callback<AgeResponse>() {
            @Override
            public void onResponse(@NonNull Call<AgeResponse> call, @NonNull Response<AgeResponse> response) {
                if (!response.isSuccessful()) {
                    runOnUiThread(() -> textView.setText("Api request failed"));
                    return;
                }

                AgeResponse ageResponse = response.body();
                if (ageResponse != null) {
                    String name = ageResponse.getName();
                    int age = ageResponse.getAge();
                    int count = ageResponse.getCount();
                    String jsonData = "Name: " + name + ", Age: " + age + ", Count: " + count;
                    Log.d("JSON", jsonData);
                    runOnUiThread(() -> textView.setText(jsonData));
                    runOnUiThread(() -> textViewAge.setText(String.valueOf(age)));
                }
            }

            @Override
            public void onFailure(@NonNull Call<AgeResponse> call, @NonNull Throwable t) {
                runOnUiThread(() -> textView.setText(t.toString()));
            }
        });
    }

    private void getAgeFromJSON(String jsonData){

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            String result = jsonObject.getString("age");

            textViewAge.setText(result);
        } catch (JSONException e) {
            textViewAge.setText("Fail");
        }

    }
}