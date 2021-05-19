package com.example.nompangadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageButton orderList, manageStorelist,addProductList, editProductList;
    private long presstime;
    private  Toast backtoast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        orderList = findViewById(R.id.orderButton);
        manageStorelist = findViewById(R.id.manageStore2);
        addProductList = findViewById(R.id.addProduct);
        editProductList = findViewById(R.id.editProduct);

        orderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toOrderlistPage =new Intent(getApplicationContext(),orderlistActivity.class);
                String a ="1";
                int aa;

                startActivity(toOrderlistPage);
            }
        });

        manageStorelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toManageStorePage = new Intent(getApplicationContext(),ChooseManageProduct.class);
                startActivity(toManageStorePage);
            }
        });

        addProductList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toaddProduct = new Intent(getApplicationContext(),addProductActivity.class);
                startActivity(toaddProduct);
            }
        });

        editProductList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toEditProduct = new Intent(getApplicationContext(),editProductActivity.class);
                startActivity(toEditProduct);
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