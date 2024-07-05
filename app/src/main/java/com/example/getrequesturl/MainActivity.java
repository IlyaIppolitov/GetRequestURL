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

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private TextView textViewAge;
    private EditText editText;
    private Button btnJson;
    private Button btnAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textViewResult);
        textViewAge = findViewById(R.id.textViewAge);
        editText = findViewById(R.id.textName);
        btnJson = findViewById(R.id.buttonValidate);;
        btnAge = findViewById(R.id.buttonAge);

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
        OkHttpClient client = new OkHttpClient();
//        String apiKey = "35369c355357da9beb54790ca3344656";
        String url ="https://api.agify.io?name=" + name;
        Log.d("JSON", url);


        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(()->textView.setText(e.toString()));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()){
                    runOnUiThread(()->textView.setText("Api request failed"));
                }else{
                    String jsonData = response.body().string();
                    Log.d("JSON", jsonData);
                    runOnUiThread(()->textView.setText(jsonData));
                }
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