package com.example.shivam.accelerometer;

import android.Manifest;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PowerManager;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by Shivam on 1/17/2018.
 */

public class MyService extends Service implements SensorEventListener {
    MediaPlayer media;
    SensorEvent sensorEvent;
    boolean bool,lol1,sen;
    Session session;
    String message,rec,subject;
    Location loc;
    Context context;
    String email2=null,email3=null,password1=null;
    Intent i;

    public void onCreate(){

        super.onCreate();

        media=MediaPlayer.create(this,R.raw.air);
        SensorManager sm=(SensorManager)this.getSystemService(SENSOR_SERVICE);
        Sensor sensor=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this,sensor,SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM);
        bool=true;
        lol1=true;
       session=null;
       sen=true;
       context=this;


       i=new Intent(MyService.this,Main2Activity.class);






        sm=(SensorManager)getSystemService(SENSOR_SERVICE);
        sensor=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this,sensor,SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM);
    }





    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null){
            email2=intent.getStringExtra("email1");
            email3=intent.getStringExtra("email2");
            password1=intent.getStringExtra("password");}
        Toast.makeText(this, "service started", Toast.LENGTH_SHORT).show();
        SensorManager sm=(SensorManager)this.getSystemService(SENSOR_SERVICE);
        Sensor sensor=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this,sensor,SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM);

        media=MediaPlayer.create(this,R.raw.air);

        Log.d("Test",intent.getStringExtra("email1"));


try {
    add();
}catch (Exception e){

}


        return START_REDELIVER_INTENT;

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service stopped", Toast.LENGTH_SHORT).show();
        media.stop();

        Messapp app=new Messapp();
        app.isCancelled();


        super.onDestroy();


    }



    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if(sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER) {
            double x = sensorEvent.values[0];
            double y = sensorEvent.values[1];
            double z = sensorEvent.values[2];
            double acc = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
            DecimalFormat precision = new DecimalFormat(("0.00"));
            double acceleration = Double.parseDouble(precision.format(acc));
            if (acceleration < 1.35d || acceleration > 34.20d&&sen) {
                sen=false;
                Intent intent=new Intent();
                PendingIntent pend=PendingIntent.getActivity(MyService.this,0,intent,0);
                Notification noti=new Notification.Builder(MyService.this)
                        .setTicker("TickerTitle")
                        .setContentTitle("Alert")
                        .setContentText("hello")
                        .setContentIntent(pend).getNotification();
                noti.flags=Notification.FLAG_AUTO_CANCEL;
                NotificationManager nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                nm.notify(0,noti);

                Toast.makeText(this, "Alert", Toast.LENGTH_SHORT).show();
                media.start();
                alert();}}}
    public void alert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are You Okay?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        media.stop();
                        dialogInterface.dismiss();


                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(lol1){
                            try {
                                lol1 = false;
                                dialogInterface.dismiss();

                                add();
                            }catch (Exception e){

                            }


                        }}
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
                if(bool){
                    try {
                        bool = false;
                        media.stop();
                        close.dismiss();
                        KeyguardManager key=(KeyguardManager)getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
                        KeyguardManager.KeyguardLock keyl=key.newKeyguardLock("TAG");
                        keyl.disableKeyguard();

                        add();
                    }catch (Exception e){

                    }

                }}
        }.start();

    }





    String date= DateFormat.getDateTimeInstance().format(new Date());



    public  class Messapp extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            try {

                Message mess = new MimeMessage(session);
                mess.setFrom(new InternetAddress(email2));
                mess.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email3));
                mess.setSubject("hello");
                mess.setContent(Location(loc.getLatitude(),loc.getLongitude())+" "+date, "text/html; charset=utf-16");


                Transport.send(mess);

            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return  null;

        }
        @Override
        protected void onPostExecute(String result){



        }


    }
    private String Location(double latitude,double longitude){
        String place="";
        Geocoder geocoder=new Geocoder(MyService.this, Locale.getDefault());
        List<Address> address;
        try{
            address= geocoder.getFromLocation(latitude,longitude,1);
            if(address.size()>0){
                place=address.get(0).getAddressLine(0)+", "+address.get(0).getAdminArea();

            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return place;
    }
    public void add(){try {
        if (ContextCompat.checkSelfPermission(MyService.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        } else {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ){
                Toast.makeText(context, "GPS is disabled!", Toast.LENGTH_LONG).show();}
            else{
                Toast.makeText(context, "GPS is enabled!", Toast.LENGTH_LONG).show();}
            loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
    }catch (Exception e){

    }
        try{


                Properties prop = new Properties();
                prop.put("mail.smtp.host", "smtp.gmail.com");
                prop.put("mail.smtp.socketFactory.port", "465");
                prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                prop.put("mail.smtp.auth", "true");
                prop.put("mail.smtp.port", "465");
                session = Session.getDefaultInstance(prop, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email2,password1 );
                    }

                });

                MyService.Messapp app = new MyService.Messapp();
                app.execute();


            }catch (Exception e){

            }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    }


