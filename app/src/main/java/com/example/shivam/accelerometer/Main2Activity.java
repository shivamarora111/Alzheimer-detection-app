package com.example.shivam.accelerometer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    EditText edit;
    EditText editText3;
    EditText editText4;
    Button button;
    Editable e;

    Intent i,j;
    public static String email,email1,password;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        edit = (EditText) findViewById(R.id.edit);
        editText3 = (EditText) findViewById(R.id.edit1);
        editText4 = (EditText) findViewById(R.id.editText4);
        button = (Button) findViewById(R.id.button);

      try{

          i = new Intent(this,MainActivity.class);


}catch(Exception e){

      }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{

                    email=edit.getText().toString();
                    email1=editText4.getText().toString();
                    password=editText3.getText().toString();

                    i.putExtra("email", email);
                    i.putExtra("email1", email1);
                    i.putExtra("password", password);



                    startActivity(i);
                    finish();
               }
                    catch (Exception e){

                  //  Toast.makeText(Main2Activity.this,e.getMessage(), Toast.LENGTH_LONG).show();

                }

            }
        });


    }




}





