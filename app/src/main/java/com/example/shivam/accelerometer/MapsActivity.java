package com.example.shivam.accelerometer;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static com.example.shivam.accelerometer.MainActivity.email;
import static com.example.shivam.accelerometer.MainActivity.email1;
import static com.example.shivam.accelerometer.MainActivity.password;

import static com.example.shivam.accelerometer.nearby_places.k;
import static com.example.shivam.accelerometer.nearby_places.place1;


public class MapsActivity extends FragmentActivity implements
 OnMapReadyCallback,
         GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

private GoogleMap mMap;
private GoogleApiClient googleApiClient;
private LocationRequest locationRequest;
private Location lastLocation;
private Marker currentUserLocationMarker;
private static final int Request_User_Location_Code = 99;
private double latitude, longitude;
private int ProximityRadius = 10000;
Context context;
ProgressDialog pd;
Session session;
public static String message="";
    public static String data="";
public static Messapp1 app;
public static String url1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        context=this;
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkUserLocationPermission();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    /*public void onclick(View v)
    {
        String hospital = "hospital", school = "school", restaurant = "restaurant";
        Object transferData[] = new Object[2];
        nearby_places getNearbyPlaces = new nearby_places();


        switch (v.getId())
        {

                case R.id.search:

                    EditText edittext = (EditText) findViewById(R.id.edittext);
                    String address = edittext.getText().toString();

                    List<Address> addressList = null;
                    MarkerOptions userMarkerOptions = new MarkerOptions();

                    if (!TextUtils.isEmpty(address)) {
                        Geocoder geocoder = new Geocoder(this);

                        try {
                            addressList = geocoder.getFromLocationName(address, 6);

                            if (addressList != null) {
                                for (int i = 0; i < addressList.size(); i++) {
                                    Address userAddress = addressList.get(i);
                                    LatLng latLng = new LatLng(userAddress.getLatitude(), userAddress.getLongitude());

                                    userMarkerOptions.position(latLng);
                                    userMarkerOptions.title(address);
                                    userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                                    mMap.addMarker(userMarkerOptions);
                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                    mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                                }
                            } else {
                                Toast.makeText(this, "Location not found...", Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(this, "please write any location name...", Toast.LENGTH_SHORT).show();
                    }
                    break;





            case R.id.Hospital:
                mMap.clear();
                String url = getUrl(latitude, longitude, hospital);
                transferData[0] = mMap;
                transferData[1] = url;

                getNearbyPlaces.execute(transferData);
                Toast.makeText(this, "Searching for Nearby Hospitals...", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Showing Nearby Hospitals...", Toast.LENGTH_SHORT).show();
                break;


            case R.id.school:
                try {
                mMap.clear();
                url = getUrl(latitude, longitude, school);
                transferData[0] = mMap;
                transferData[1] = url;


                    getNearbyPlaces.execute(transferData);
                    Toast.makeText(this, "Searching for Nearby Schools...", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "Showing Nearby Schools...", Toast.LENGTH_SHORT).show();
                    break;
                }catch (Exception e){
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            case R.id.restaurant:
                mMap.clear();
                url = getUrl(latitude, longitude, restaurant);
                transferData[0] = mMap;
                transferData[1] = url;

                getNearbyPlaces.execute(transferData);
                Toast.makeText(this, "Searching for Nearby Restaurants...", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Showing Nearby Restaurants...", Toast.LENGTH_SHORT).show();
                break;
        }
    }*/




    private String getUrl(double latitude, double longitude, String nearbyPlace)
    {
        StringBuilder googleURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googleURL.append("location=" + latitude + "," + longitude);
        googleURL.append("&radius=" + ProximityRadius);
        googleURL.append("&type=" + nearbyPlace);
        googleURL.append("&sensor=true");
        googleURL.append("&key=" + "AIzaSyA99PNGRQlKtCm_PcVNzdTzxElXmkpSXnU");

        Log.d("GoogleMapsActivity", "url = " + googleURL.toString());
      message=googleURL.toString();

        return googleURL.toString();
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            buildGoogleApiClient();

            mMap.setMyLocationEnabled(true);
        }

    }



    public boolean checkUserLocationPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            }
            return false;
        }
        else
        {
            return true;
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case Request_User_Location_Code:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                        if (googleApiClient == null)
                        {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(this, "Permission Denied...", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }




    protected synchronized void buildGoogleApiClient()
    {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }


    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        lastLocation = location;

        if (currentUserLocationMarker != null) {
            currentUserLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("user Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

        currentUserLocationMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(12));
        String hospital = "hospital", school = "school", restaurant = "restaurant";
        Object transferData[] = new Object[2];
        nearby_places getNearbyPlaces = new nearby_places();
        mMap.clear();
        String url1 = getUrl(latitude, longitude, hospital);
        transferData[0] = mMap;
        transferData[1] = url1;

        getNearbyPlaces.execute(transferData);
        Toast.makeText(this, "Searching for Nearby Hospitals...", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Showing Nearby Hospitals...", Toast.LENGTH_SHORT).show();

        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
        try {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();

            //  fetchData fetchdata = new fetchData();

            //fetchdata.execute();

            new CountDownTimer(10000, 1000) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
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


                    app = new Messapp1();

                    pd = ProgressDialog.show(context, "", "sending mail...", true);
                    Toast.makeText(context, k, Toast.LENGTH_LONG).show();


                    // near();


                    app.execute();

                }
            }.start();





        } catch (Exception e) {

        }


    }


    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }


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


    private  class Messapp1 extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... string) {

            try {



                    Message mess = new MimeMessage(session);
                    mess.setFrom(new InternetAddress(email));
                    mess.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email1));
                    mess.setSubject("Alert!!!");
                    //mess.setText(nearbyhospitals.l.toString());

                    mess.setText(message + nearby_places.k);


                    //mess.setText(pla.toString());
                    Transport.send(mess);


            } catch (MessagingException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String result) {


            pd.dismiss();
            if (new MapsActivity.connectivity(MapsActivity.this).isNetworkAvailable()) {

                Toast.makeText(context, "message sent", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(context, "no internet connection", Toast.LENGTH_SHORT).show();
            }
        }
    }
        @Override
        public void onConnectionSuspended(int i) {

        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        }
    }

