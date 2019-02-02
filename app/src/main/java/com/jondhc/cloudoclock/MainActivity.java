package com.jondhc.cloudoclock;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    Retrofit retrofit;
    RestClient restClient;
    TextView serverTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.activity_main);
    } //end onCreate

    public void setRetrofit(String ipAddress, String portNumber){
        serverTime = findViewById(R.id.serverTime);
        retrofit = new Retrofit.Builder().baseUrl("http://"+ipAddress + ":" + portNumber).addConverterFactory(ScalarsConverterFactory.create()).build();
        restClient = retrofit.create(RestClient.class);
    } //end setRetrofit

    public void start(View view){
        EditText ipAddress = findViewById(R.id.ipAddress);
        EditText portNumber = findViewById(R.id.portNumber);
        EditText timeInterval = findViewById(R.id.interval);
        String ip = ipAddress.getText().toString();
        String port = portNumber.getText().toString();
        final int interval = Integer.parseInt(timeInterval.getText().toString()) * 1000;
        setRetrofit(ip, port);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                update();
                handler.postDelayed(this, interval);
            } //end run
        }, interval);
    } //end start


    public void setLocalTime(){
        TextView localTime = findViewById(R.id.localTime);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedTime = timeFormat.format(c.getTime());
        localTime.setText(formattedTime);
    } //end setLocalTime

    public void setServerTime(){
        Call<String> call = restClient.getData();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String data = "";
                switch (response.code()){
                    case 200:
                        data = response.body();
                        serverTime.setText(data);
                        //showLongToast("This is the data" + data);
                        break;
                    default:
                        showLongToast("Case not handled");
                } //end switch-case
            } //end onResponse

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                showLongToast(t.getMessage());
            } //onFailure
        }); //call enqueue
    } //end setServerTime


    public void update(){
        setLocalTime();
        setServerTime();
    } //end update

    public void showLongToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    } //end showLongToast

    public void showShortToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    } //end showShortToast

    public void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    } //end setFullScreen

} //end MainActivity
