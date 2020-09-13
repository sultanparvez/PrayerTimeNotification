package com.example.prayertimenotification;

import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.AlarmClock;
import android.util.AttributeSet;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class dailytime extends AppCompatActivity {
    Button bt;
    Button bt2;
    String data;
    String date="";
    public static String fajar="";
    String juhur="";
    String asar="";
    String mugrib="";
    String Isha="";
    String currentdate;
    public static TextView datet;
    public static TextView fazart;
    public static TextView juhurt;
    public static TextView asart;
    public static TextView mugribt;
    public static TextView ishat;

    public static Button fazarButton;
    public static Button juhurButton;
    public static Button asarButton;
    public static Button mugribButton;
    public static Button eshaButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_frag);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        doInBackground();
        fazart=(TextView)findViewById(R.id.fazarTime);
        juhurt=(TextView)findViewById(R.id.juhurTime);
        asart=(TextView)findViewById(R.id.asarTime);
        mugribt=(TextView)findViewById(R.id.mugribTime);
        ishat=(TextView)findViewById(R.id.eshaTime);
        datet=(TextView)findViewById(R.id.dateNum);
        bt=findViewById(R.id.btn_home);
        bt2=findViewById(R.id.btn_map2);
        fazart.setText((String)fajar);
        juhurt.setText((String)juhur);
        asart.setText((String)asar);
        mugribt.setText((String)mugrib);
        ishat.setText((String)Isha);
        datet.setText((String) currentdate);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(dailytime.this,MainActivity.class);
                startActivity(i);
                finish();
                Toast.makeText(getApplicationContext(),"loading homepage",Toast.LENGTH_SHORT).show();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(dailytime.this,maplocator.class);
                startActivity(i);
                finish();
                Toast.makeText(getApplicationContext(),"Loading Map",Toast.LENGTH_SHORT).show();
            }
        });
        fazarButton=(Button)findViewById(R.id.fazarButton);
        juhurButton=(Button)findViewById(R.id.juhurbutton);
        asarButton=(Button)findViewById(R.id.asarButton);
        mugribButton=(Button)findViewById(R.id.mugribButton);
        eshaButton=(Button)findViewById(R.id.eshaButton);
        fazarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AlarmClock.ACTION_SET_ALARM);
                i.putExtra(AlarmClock.EXTRA_HOUR,getTime(fajar)[0]);
                i.putExtra(AlarmClock.EXTRA_MINUTES,getTime(fajar)[1]);
                startActivity(i);
            }
        });
        juhurButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AlarmClock.ACTION_SET_ALARM);
                i.putExtra(AlarmClock.EXTRA_HOUR,getTime(juhur)[0]);
                i.putExtra(AlarmClock.EXTRA_MINUTES,getTime(juhur)[1]);
                startActivity(i);
            }
        });
        asarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AlarmClock.ACTION_SET_ALARM);
                i.putExtra(AlarmClock.EXTRA_HOUR,getTime(asar)[0]);
                i.putExtra(AlarmClock.EXTRA_MINUTES,getTime(asar)[1]);
                startActivity(i);
            }
        });
        mugribButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AlarmClock.ACTION_SET_ALARM);
                i.putExtra(AlarmClock.EXTRA_HOUR,getTime(mugrib)[0]);
                i.putExtra(AlarmClock.EXTRA_MINUTES,getTime(mugrib)[1]);
                startActivity(i);
            }
        });
        eshaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AlarmClock.ACTION_SET_ALARM);
                i.putExtra(AlarmClock.EXTRA_HOUR,getTime(Isha)[0]);
                i.putExtra(AlarmClock.EXTRA_MINUTES,getTime(Isha)[1]);
                startActivity(i);
            }
        });

    }

    protected Void doInBackground(Void... voids) {
        Calendar calendar = Calendar.getInstance();
         currentdate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        Log.d("check",currentdate);

        try {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String jsonUrl="https://api.pray.zone/v2/times/today.json?city=Dhaka";
            URL url= new URL(jsonUrl);
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            InputStream in=httpURLConnection.getInputStream();
           BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(in));
            String line= "a";
            while(line!=null){
                line=bufferedReader.readLine();
               data= data+line;
          }
            Log.d("debug1",data);

          String crappyPrefix = "null";

           if(data.startsWith(crappyPrefix)){
                data = data.substring(crappyPrefix.length(), data.length());
          }

           JSONObject jsnobject = new JSONObject(data);
            JSONObject ja=jsnobject.getJSONObject("results");
          // Log.d("debug2", String.valueOf(jsnobject));
           // Log.d("debug2", String.valueOf(ja));
            JSONArray ja2=ja.getJSONArray("datetime");
            JSONObject ja3=ja2.getJSONObject(0);
           // Log.d("debuglast", String.valueOf(ja3));
            JSONObject ja4=ja3.getJSONObject("times");


               //JSONObject jo=(JSONObject)ja3.getJSONObject(i);
                //date = (String) jo.get("date_for");
                fajar = ja4.getString("Fajr");
            Log.d("debugfajr", String.valueOf(fajar));
                juhur = ja4.getString("Dhuhr");
            Log.d("debugfajr", String.valueOf(juhur));
               asar = ja4.getString("Asr");
            Log.d("debugfajr", String.valueOf(asar ));
               mugrib = ja4.getString("Maghrib");
            Log.d("debugfajr", String.valueOf(mugrib ));
                Isha = ja4.getString("Isha");
            Log.d("debugfajr", String.valueOf(Isha));



        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("url", "URL er probl");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("IO", "IO te problem");
        } catch (JSONException e) {
            e.printStackTrace();
       }

        finally {
            //timingFragment.f.concat(""+this.fajar);

        }

        return null;

    }

    public String getData() {
        return this.fajar;
    }
    private int[] getTime(String time){
        String[] separated1 = time.split(":");

        int[] arr = new int[2];
        arr[0] = Integer.parseInt( separated1[0]);
        arr[1] = Integer.parseInt( separated1[1]);
        return arr;
    }

}

