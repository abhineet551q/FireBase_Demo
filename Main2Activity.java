package com.aarawholesale.myquiz.firebase_demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class Main2Activity extends AppCompatActivity {

    TextView username,password;
    Button b1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        username = findViewById(R.id.editText4);

        password = findViewById(R.id.editText6);

        b1 = findViewById(R.id.button15);

     final   HashMap<String,String> matches = new HashMap<>();
        matches.put("abhineet","Cosmoses123");

        matches.put("sulekha","Cosmoses123");

        matches.put("dinesh","Cosmoses123");

        matches.put("saikrishna","Cosmoses123");

       // String unm = (String) username.getText();
      //  String pwd =  (String) password.getText();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login(matches);



            }
        });





    }
   void login(HashMap matches){


       String    unm = username.getText().toString();
       String     pwd = password.getText().toString();

       if(unm.equals("abhineet")||unm.equals("sulekha")||unm.equals("saikrishna")||unm.equals("dinesh")){
           if(pwd.equals(matches.get(unm))){

               Intent intent = new Intent(this,MainActivity.class);
               startActivity(intent);
           }
           else{

               Toast.makeText(Main2Activity.this, "Wrong password",
                       Toast.LENGTH_LONG).show();
           }
       }
       else{
           Toast.makeText(Main2Activity.this, "Wrong Username",
                   Toast.LENGTH_LONG).show();

       }


   }
}
