package com.example.nompangadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nompangadmin.models.ManageProductViewholder;
import com.example.nompangadmin.models.models;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Manage_Bread extends AppCompatActivity {
    private RecyclerView manage_product;

    private Switch CheckBread;

    public String Test;

    RecyclerView.LayoutManager layoutManager;

    private Toast backtoast;
    private long presstime;

    private DatabaseReference DatabaseBread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_bread);

        DatabaseBread = FirebaseDatabase.getInstance().getReference().child("Product").child("Bread");

        CheckBread = findViewById(R.id.ManageProductStatus);

        manage_product = findViewById(R.id.manageStore);
        layoutManager = new LinearLayoutManager(this);
        manage_product.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<models> Bread =
                new FirebaseRecyclerOptions.Builder<models>()
                        .setQuery(DatabaseBread, models.class)
                        .build();
        FirebaseRecyclerAdapter<models, ManageProductViewholder> adapter =
                new FirebaseRecyclerAdapter<models, ManageProductViewholder>(Bread) {
            @Override
            protected void onBindViewHolder(@NonNull ManageProductViewholder holder, int position, @NonNull models model) {
                String Position = String.valueOf(position+1);

                Test = getRef(position).getKey();
                holder.NameProduct.setText(model.getName());
                SetProduct(Test, holder);

                holder.StatusProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseBread.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                System.out.println(model.getName());
                                HashMap<String, Object> UpdateStatus = new HashMap<>();
                                if(holder.StatusProduct.isChecked()){
                                    System.out.println("stock");
                                    UpdateStatus.put("status","stock");
                                }
                                else if(!holder.StatusProduct.isChecked()) {
                                    System.out.println("out of stock");
                                    UpdateStatus.put("status","out of stock");
                                }
                                DatabaseBread.child(Position).updateChildren(UpdateStatus);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
            }
            @NonNull
            @Override
            public ManageProductViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_order_layout, parent, false);
                ManageProductViewholder  holder = new ManageProductViewholder(view);
                return  holder;
            }
        };
        manage_product.setAdapter(adapter);
        adapter.startListening();
    }

    public void SetProduct(String i, ManageProductViewholder holder){
        DatabaseBread.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(i).child("status").getValue().toString().equals("stock")){
                    holder.StatusProduct.setChecked(true);
                }
                else {
                    holder.StatusProduct.setChecked(false);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    public void onBackPressed(){
        if(presstime+2000>System.currentTimeMillis()){
            backtoast.cancel();
            startActivity(new Intent(Manage_Bread.this,ChooseManageProduct.class));
            return;
        }
        else{
            backtoast =  Toast.makeText(this, "กดอีกครั้งเพื่อย้อน", Toast.LENGTH_SHORT);
            backtoast.show();
        }
        presstime = System.currentTimeMillis();
    }

}