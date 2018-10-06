package com.example.shivam.accelerometer;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;

import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.location.LocationListener;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Timer;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MainActivity extends AppCompatActivity implements
        SensorEventListener,
        LocationListener

{
    TextView textView;
    public boolean complete = false;
    public String[] pl1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    LocationRequest locationRequest;
    private boolean bool, lol, lol1, lol2;
    SensorManager sm;

    Sensor sensor;
    String location1;
    String s = "";

    Button button;
    public static Session session = null;
    public static ProgressDialog pd = null;
    Context context;
    MediaPlayer media;
    Location loc;
    LocationManager lm;
    // Location location;

    String message, rec, subject;
    public static String email;
    public static String password;
    public static String email1;
    public static final int Requestcode = 99;
    GoogleApiClient googleApiClient;
    String uri;
    boolean bound = false;
    Double latitude, longitude;
    int area = 10000;
    List<String> pla = new ArrayList<>();
    String place;
    private GoogleMap mMap;
    public static Messapp app;
    LocationManager locationManager;
    LocationListener locationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        context = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            check();
        }
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, sensor, SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        textView6 = (TextView) findViewById(R.id.textView6);
        button = (Button) findViewById(R.id.button);
        bool = true;
        lol = true;
        lol1 = true;
        lol2 = true;
        try {
            locationRequest = new LocationRequest();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 1000, 0, locationListener);

            }

            Intent i = getIntent();


            email = i.getStringExtra("email");
            password = i.getStringExtra("password");
            email1 = i.getStringExtra("email1");
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
                Toast.makeText(context, "GPS is disabled!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "GPS is enabled!", Toast.LENGTH_LONG).show();

            }


            media = MediaPlayer.create(this, R.raw.air);


        }catch (Exception e){

        }
    }

    private String Location(double latitude, double longitude) {
        String place = "";
        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
        List<Address> address;
        try {
            address = geocoder.getFromLocation(latitude, longitude, 1);
            if (address.size() > 0) {
                place = address.get(0).getAddressLine(0) + ", " + address.get(0).getAdminArea();


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return place;
    }

    public void add() {
        try {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                }
            } else {


                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
                    Toast.makeText(context, "GPS is disabled!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "GPS is enabled!", Toast.LENGTH_LONG).show();

                }
                loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            }
        } catch (Exception e) {

        }



          /*  try {
                uri = "geo:" + loc.getLatitude() + "," + loc.getLongitude() + "&q=child hospitals";

                textView4.setText(Location(latitude, longitude));
                textView5.setText(latitude.toString());
                textView6.setText(longitude.toString());
                textView4.setText(uri);

            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "not found", Toast.LENGTH_SHORT).show();
            }*/


        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "465");
        session = Session.getDefaultInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }

        });

        pd = ProgressDialog.show(context, "", "sending mail...", true);
        app = new Messapp();


        // near();


        app.execute();


    }


    //@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    //@Override

    public void onSensorChanged(final SensorEvent sensorEvent) {
        textView.setText("X: " + sensorEvent.values[0]);
        textView2.setText("Y: " + sensorEvent.values[1]);
        textView3.setText("Z: " + sensorEvent.values[2]);
        try{
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            double x = sensorEvent.values[0];
            double y = sensorEvent.values[1];
            double z = sensorEvent.values[2];
            double acc = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
            DecimalFormat precision = new DecimalFormat(("0.00"));
            double acceleration = Double.parseDouble(precision.format(acc));
            if (lol1 && acceleration < 1.35d || acceleration > 34.20d) {
                lol1 = false;
                //  Intent intent=new Intent();
                /*PendingIntent pend=PendingIntent.getActivity(MainActivity.this,0,intent,0);
                Notification noti=new Notification.Builder(MainActivity.this)
                        .setTicker("TickerTitle")
                        .setContentTitle("Alert")
                        .setContentText("hello")
                        .setContentIntent(pend).getNotification();
                noti.flags=Notification.FLAG_AUTO_CANCEL;
                NotificationManager nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                nm.notify(0,noti);*/

                Toast.makeText(this, "Alert", Toast.LENGTH_SHORT).show();
                media.start();
                alert();
            }
            }
        }catch (Exception e){

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    public void alert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are You Okay?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        lol2 = false;

                        media.stop();
                        dialogInterface.dismiss();
                    }


                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        try {
                            lol1 = false;
                            dialogInterface.dismiss();


                            add();


                        } catch (Exception e) {

                        }


                    }
                });

        final AlertDialog close = builder.create();

        try {

            close.show();
        } catch (Exception e) {

        }
        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {

                if (close.isShowing()) {
                    media.stop();
                    close.dismiss();


                    add();


                }

            }


        }.start();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Requestcode:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


                        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                        loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);


                    }
                } else {
                    Toast.makeText(this, "no permission granted", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }





   /* public synchronized void GoogleClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }*/


    public boolean check() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Requestcode);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Requestcode);
            }
            return false;
        } else {
            return true;
        }


    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            latitude = loc.getLatitude();
            longitude = loc.getLongitude();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 1000, 0, this);
            }
        }catch(Exception e){

        }



    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    private class connectivity {
        private Context context;
        public connectivity(Context context){
            this.context=context;

        }
        public boolean isNetworkAvailable(){
            ConnectivityManager conn=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo net=conn.getActiveNetworkInfo();
            return  net!=null && net.isConnected();
        }
    }

String date= DateFormat.getDateTimeInstance().format(new Date());


  private  class Messapp extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... string) {

            try {


                Message mess = new MimeMessage(session);
                mess.setFrom(new InternetAddress(email));
                mess.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email1));
                mess.setSubject("Alert!!!");
                //mess.setText(nearbyhospitals.l.toString());
                mess.setContent(Location(loc.getLatitude(),loc.getLongitude())+"\n"+date+"\n"+"https://www.google.com/maps/search/?api=1&query="+loc.getLatitude()+","+loc.getLongitude(), "text/html; charset=utf-16");
                //mess.setText(s);



                //mess.setText(pla.toString());
                Transport.send(mess);

            } catch (MessagingException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return  null;

        }
        @Override
        protected void onPostExecute(String result){


                pd.dismiss();
                if (new connectivity(MainActivity.this).isNetworkAvailable()) {

                    Toast.makeText(context, "message sent", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "no internet connection", Toast.LENGTH_SHORT).show();
                }
            Intent intent;
            intent = new Intent(MainActivity.this,MapsActivity.class);
            startActivity(intent);
            }




        }


















    public void start(View view){

        Intent intent=new Intent(this,MyService.class);




        intent.putExtra("email",email);
        intent.putExtra("email1",email1);
        intent.putExtra("password",password);

        Toast.makeText(context, "service started", Toast.LENGTH_LONG).show();
        startService(intent);

    }

    public void stop(View view){

        Intent intent=new Intent(this,MyService.class);
        Toast.makeText(context, "service stopped", Toast.LENGTH_LONG).show();


        stopService(intent);
       // Messapp app=new Messapp();
       // app.isCancelled();







    }





}