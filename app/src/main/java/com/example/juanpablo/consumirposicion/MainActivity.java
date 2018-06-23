package com.example.juanpablo.consumirposicion;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //tvUbicacion = (TextView)findViewById(R.id.tvUbicacion);
        tvLatitud = (TextView)findViewById(R.id.tvLatitud);
        tvLongitud = (TextView)findViewById(R.id.tvLongitud);
        //btnGPS = (Button)findViewById(R.id.btnGPS);

        rq = Volley.newRequestQueue(this);
        consumirPosicion();




    }

    private void consumirPosicion( ){

       String url = "http://192.168.0.85/login/consumirPosicion.php?";
        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);

        rq.add(jrq);
    }


/*
    private void compartirPosicion( Double lat, Double lon ){

        String url = "http://192.168.0.82/login/actualizarPosicion.php?x="+lat.toString()+"&y="+lon.toString();

        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }
*/

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

/*
            arr = new String[jsonArray.length()];
            for(int i = 0; i < jsonArray.length(); i++){

                //arr[i] = jsonArray.getString(i);
                arr[i] = jsonArray.getJSONObject(i).toString();

                }

            tvLatitud.setText(arr[0]);
            tvLongitud.setText(arr[1]);
*/

        }catch (JSONException e){
            e.printStackTrace();
        }

        Toast.makeText(this,"todo ok: ", Toast.LENGTH_SHORT).show();

    }
}


