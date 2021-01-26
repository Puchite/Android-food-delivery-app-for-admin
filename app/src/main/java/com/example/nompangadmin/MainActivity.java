package com.example.nompangadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Button orderList,manageStorelist;
    private long presstime;
    private  Toast backtoast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        orderList = findViewById(R.id.orderButton);
        manageStorelist = findViewById(R.id.manageStore);


        orderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toOrderlistPage =new Intent(getApplicationContext(),orderlistActivity.class);
                String a ="1";
                int aa;


//                final DatabaseReference roota;
//
//                roota = FirebaseDatabase.getInstance().getReference();
//
//                roota.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
////                        roota.child("Drinks").child("coffe").removeValue();
//                        HashMap<String,Object> userdataMap = new HashMap<>();
//                        userdataMap.put("name","nompang+sang");
//                        userdataMap.put("price","50");
//                        userdataMap.put("amout","2");
//                        userdataMap.put("description","ควย");
//                        roota.child("Orders").child("3").updateChildren(userdataMap);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
                startActivity(toOrderlistPage);
            }
        });

        manageStorelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toManageStorePage = new Intent(getApplicationContext(),manageStoreActivity.class);
                startActivity(toManageStorePage);
            }
        });



    }
    public void onBackPressed() {

        if(presstime+2000>System.currentTimeMillis()){
            backtoast.cancel();
            finishAffinity();
            return;
        }
        else{

            backtoast =  Toast.makeText(this, "กดอีกครั้งเพื่อออก", Toast.LENGTH_SHORT);
                    backtoast.show();
        }
        presstime = System.currentTimeMillis();
    }
}