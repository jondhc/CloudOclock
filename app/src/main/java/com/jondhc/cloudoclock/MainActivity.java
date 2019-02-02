package com.jondhc.cloudoclock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView serverTime = findViewById(R.id.serverTime);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.74:8000").build();
        RestClient restClient = retrofit.create(RestClient.class);
        Call<String> call = restClient.getData();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String data = "";
                switch (response.code()){
                    case 200:
                        data = response.body();
                        //serverTime.setText(data);
                        break;
                } //end switch-case
            } //end onResponse

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Error",t.getMessage());
            } //onFailure
        }); //call enqueue
        setLocalTime();
    } //end onCreate

    public void setLocalTime(){
        TextView localTime = findViewById(R.id.localTime);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedTime = timeFormat.format(c.getTime());
        localTime.setText(formattedTime);
    } //end setLocalTime

    public void setServerTime(){

    } //end setServerTime

    public void setRetrofit(){


    } //end setRetrofit


    public void update(View view){
        setLocalTime();
    } //end update

} //end MainActivity
