package com.example.shivam.accelerometer;

import android.os.AsyncTask;

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

/**
 * Created by Shivam on 9/16/2018.
 */

public class fetchData extends AsyncTask<Void,Void,Void> {
    String data="";
    String dataParse="";
    String single="";
    @Override
    protected Void doInBackground(Void... voids) {


        try {
            URL url=new URL(MapsActivity.message);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while(line!=null){
                line=bufferedReader.readLine();
                data+=line;

            }
            JSONArray jsonArray=new JSONArray(data);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=(JSONObject)jsonArray.get(i);
                single="Name: "+jsonObject.get("name")+"\n"+
                        "Vicinity: "+jsonObject.get("vicinity")+"\n";
                dataParse=single+dataParse;


            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public  void onPostExecute(Void avoid){
        MapsActivity.data.concat(dataParse);


    }
}
