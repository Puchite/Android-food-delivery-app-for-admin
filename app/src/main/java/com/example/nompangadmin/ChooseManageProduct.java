package com.example.nompangadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ChooseManageProduct extends AppCompatActivity {

    ImageButton ManageBread, ManageDrinks;

    private Toast backtoast;
    private long presstime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosemanageproduct);

        ManageBread = findViewById(R.id.Bread);
        ManageDrinks = findViewById(R.id.Drinks);

        ManageBread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ToManageBread = new Intent(getApplicationContext(), Manage_Bread.class);
                startActivity(ToManageBread);
            }
        });
        ManageDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ToManageDrinks = new Intent(getApplicationContext(),Manage_Drinks.class);
                startActivity(ToManageDrinks);
            }
        });
    }

    public void onBackPressed(){
        if(presstime+2000>System.currentTimeMillis()){
            backtoast.cancel();
            startActivity(new Intent(ChooseManageProduct.this, MainActivity.class));
            return;
        }
        else{
            backtoast =  Toast.makeText(this, "กดอีกครั้งเพื่อย้อน", Toast.LENGTH_SHORT);
            backtoast.show();
        }
        presstime = System.currentTimeMillis();
    }
}
