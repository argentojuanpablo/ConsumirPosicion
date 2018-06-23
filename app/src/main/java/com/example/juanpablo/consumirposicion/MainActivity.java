package com.example.juanpablo.consumirposicion;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;

import static android.widget.Toast.makeText;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {


    //Button btnGPS;
    //TextView tvUbicacion;
    TextView tvLatitud;
    TextView tvLongitud;
    String[] arr;

    RequestQueue rq;
    JsonRequest jrq;

    time time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLatitud = (TextView)findViewById(R.id.tvLatitud);
        tvLongitud = (TextView)findViewById(R.id.tvLongitud);
        rq = Volley.newRequestQueue(this);
        time = new time();
        time.execute();


    }

    public void hilo(){
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void ejecutar(){
        time time = new time();
        Log.d("time: ", "adentro de time");
        time.execute();
    }

    public class time extends AsyncTask<Void,Integer,Boolean>
    {

        @Override
        protected Boolean doInBackground(Void... voids) {

            for (int i=1; i<=2 ;i++){
                hilo();
                if (isCancelled()) break;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            ejecutar();
            consumirPosicion();
            Toast.makeText(MainActivity.this,"Consumiendo...",Toast.LENGTH_SHORT).show();

        }
    }


    private void consumirPosicion( ){

       String url = "https://virginal-way.000webhostapp.com/consumirPosicionONLINE.php?";
       //String url = "http://192.168.0.70/login/consumirPosicion.php?";
       Log.d("url1",url);
       jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
       rq.add(jrq);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(this,"errorcito: "+ error.toString(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray jsonArray = response.optJSONArray("coordenadas");
        JSONObject jsonObject = null;
        try{

            jsonObject = jsonArray.getJSONObject(0);
            tvLatitud.setText(jsonObject.optString("latitud"));
            tvLongitud.setText(jsonObject.optString("longitud"));

        }catch (JSONException e){
            e.printStackTrace();
        }

        Toast.makeText(this,"TODO OK ", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        time.cancel(true);
        super.onBackPressed();

    }
}


