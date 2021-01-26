package com.example.nompangadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Locale;

public class manageStoreActivity extends AppCompatActivity {
    TextView item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11,item12;

    SwitchCompat checkButton1,checkButton2,checkButton3,checkButton4,checkButton5,checkButton6,checkButton7,checkButton8,checkButton9,checkButton10,checkButton11,checkButton12;
    Button updateButton;
    private  Toast backtoast;
    private long presstime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_store);
        item1 = findViewById(R.id.productCategory1);
        item2 = findViewById(R.id.productCategory2);
        item3 = findViewById(R.id.productCategory3);
        item4 = findViewById(R.id.productCategory4);
        item5 = findViewById(R.id.productCategory5);
        item6 = findViewById(R.id.productCategory6);
        item7 = findViewById(R.id.productCategory7);
        item8 = findViewById(R.id.productCategory8);
        item9 = findViewById(R.id.productCategory9);
        item10 = findViewById(R.id.productCategory10);
        item11 = findViewById(R.id.productCategory11);
        item12 = findViewById(R.id.productCategory12);
        updateButton = findViewById( R.id.update_button);
        final DatabaseReference ref;
        View view;

        checkButton1 = findViewById(R.id.check1);
        checkButton2 = findViewById(R.id.check2);
        checkButton3 = findViewById(R.id.check3);
        checkButton4 = findViewById(R.id.check4);
        checkButton5 = findViewById(R.id.check5);
        checkButton6 = findViewById(R.id.check6);
        checkButton7 = findViewById(R.id.check7);
        checkButton8 = findViewById(R.id.check8);
        checkButton9 = findViewById(R.id.check9);
        checkButton10 = findViewById(R.id.check10);
        checkButton11 = findViewById(R.id.check11);
        checkButton12 = findViewById(R.id.check12);


        final int[] i = {1};
        ref = FirebaseDatabase.getInstance().getReference().child("Product");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                item1.setText(snapshot.child("Drinks").child("1").child("name").getValue().toString());
                item2.setText(snapshot.child("Drinks").child("2").child("name").getValue().toString());
                item3.setText(snapshot.child("Drinks").child("3").child("name").getValue().toString());
                item4.setText(snapshot.child("Drinks").child("4").child("name").getValue().toString());
                item5.setText(snapshot.child("Drinks").child("5").child("name").getValue().toString());
                item6.setText(snapshot.child("Drinks").child("6").child("name").getValue().toString());
                item7.setText(snapshot.child("Drinks").child("7").child("name").getValue().toString());
                item8.setText(snapshot.child("Drinks").child("8").child("name").getValue().toString());
                item9.setText(snapshot.child("Drinks").child("9").child("name").getValue().toString());
                item10.setText(snapshot.child("Drinks").child("10").child("name").getValue().toString());
                item11.setText(snapshot.child("Bread").child("1").child("name").getValue().toString());
                item12.setText(snapshot.child("Bread").child("2").child("name").getValue().toString());

                if (snapshot.child("Drinks").child("1").child("status").getValue().toString().equals("stock")) {
                    checkButton1.setChecked(true);
                }
                if (snapshot.child("Drinks").child("2").child("status").getValue().toString().equals("stock")) {
                    checkButton2.setChecked(true);
                }
                if (snapshot.child("Drinks").child("3").child("status").getValue().toString().equals("stock")) {
                    checkButton3.setChecked(true);
                }
                if (snapshot.child("Drinks").child("4").child("status").getValue().toString().equals("stock")) {
                    checkButton4.setChecked(true);
                }
                if (snapshot.child("Drinks").child("5").child("status").getValue().toString().equals("stock")) {
                    checkButton5.setChecked(true);
                }
                if (snapshot.child("Drinks").child("6").child("status").getValue().toString().equals("stock")) {
                    checkButton6.setChecked(true);
                }
                if (snapshot.child("Drinks").child("7").child("status").getValue().toString().equals("stock")) {

                    checkButton7.setChecked(true);
                }
                if (snapshot.child("Drinks").child("8").child("status").getValue().toString().equals("stock")) {
                    checkButton8.setChecked(true);
                }
                if (snapshot.child("Drinks").child("9").child("status").getValue().toString().equals("stock")) {
                    checkButton9.setChecked(true);
                }
                if (snapshot.child("Drinks").child("10").child("status").getValue().toString().equals("stock")) {
                    checkButton10.setChecked(true);
                }
                if (snapshot.child("Bread").child("1").child("status").getValue().toString().equals("stock")) {

                    checkButton11.setChecked(true);
                }
                if (snapshot.child("Bread").child("2").child("status").getValue().toString().equals("stock")) {
                    checkButton12.setChecked(true);
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final  DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Product");
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        HashMap<String, Object> userData = new HashMap<>();
                        if (checkButton1.isChecked()){
                            userData.put("status","stock");
                            reff.child("Drinks").child("1").updateChildren(userData);
                        }
                        else{
                            userData.put("status","out of stock");
                            reff.child("Drinks").child("1").updateChildren(userData);
                        }
                        if (checkButton2.isChecked()){
                            userData.put("status","stock");
                            reff.child("Drinks").child("2").updateChildren(userData);
                        }
                        else{
                            userData.put("status","out of stock");
                            reff.child("Drinks").child("2").updateChildren(userData);
                        }
                        if (checkButton3.isChecked()){
                            userData.put("status","stock");
                            reff.child("Drinks").child("3").updateChildren(userData);
                        }
                        else{
                            userData.put("status","out of stock");
                            reff.child("Drinks").child("3").updateChildren(userData);
                        }
                        if (checkButton4.isChecked()){
                            userData.put("status","stock");
                            reff.child("Drinks").child("4").updateChildren(userData);
                        }
                        else{
                            userData.put("status","out of stock");
                            reff.child("Drinks").child("4").updateChildren(userData);
                        }
                        if (checkButton5.isChecked()){
                            userData.put("status","stock");
                            reff.child("Drinks").child("5").updateChildren(userData);
                        }
                        else{
                            userData.put("status","out of stock");
                            reff.child("Drinks").child("5").updateChildren(userData);
                        }
                        if (checkButton6.isChecked()){
                            userData.put("status","stock");
                            reff.child("Drinks").child("6").updateChildren(userData);
                        }
                        else{
                            userData.put("status","out of stock");
                            reff.child("Drinks").child("6").updateChildren(userData);
                        }
                        if (checkButton7.isChecked()){
                            userData.put("status","stock");
                            reff.child("Drinks").child("7").updateChildren(userData);
                        }
                        else{
                            userData.put("status","out of stock");
                            reff.child("Drinks").child("7").updateChildren(userData);
                        }
                        if (checkButton8.isChecked()){
                            userData.put("status","stock");
                            reff.child("Drinks").child("8").updateChildren(userData);
                        }
                        else{
                            userData.put("status","out of stock");
                            reff.child("Drinks").child("8").updateChildren(userData);
                        }if (checkButton9.isChecked()){
                            userData.put("status","stock");
                            reff.child("Drinks").child("9").updateChildren(userData);
                        }
                        else{
                            userData.put("status","out of stock");
                            reff.child("Drinks").child("9").updateChildren(userData);
                        }
                        if (checkButton10.isChecked()){
                            userData.put("status","stock");
                            reff.child("Drinks").child("10").updateChildren(userData);
                        }
                        else{
                            userData.put("status","out of stock");
                            reff.child("Drinks").child("10").updateChildren(userData);
                        }
                        if (checkButton11.isChecked()){
                            userData.put("status","stock");
                            reff.child("Bread").child("1").updateChildren(userData);
                        }
                        else{
                            userData.put("status","out of stock");
                            reff.child("Bread").child("1").updateChildren(userData);
                        }
                        if (checkButton12.isChecked()){
                            userData.put("status","stock");
                            reff.child("Bread").child("2").updateChildren(userData);
                        }
                        else{
                            userData.put("status","out of stock");
                            reff.child("Bread").child("2").updateChildren(userData);
                        }
                        startActivity(new Intent(manageStoreActivity.this,manageStoreActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });



    }
    public void onBackPressed() {

        if(presstime+2000>System.currentTimeMillis()){
            backtoast.cancel();
            startActivity(new Intent(manageStoreActivity.this,MainActivity.class));
            return;
        }
        else{
            backtoast =  Toast.makeText(this, "กดอีกครั้งเพื่อย้อน", Toast.LENGTH_SHORT);
                    backtoast.show();
        }
        presstime = System.currentTimeMillis();
    }
}