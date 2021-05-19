package com.example.nompangadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nompangadmin.models.AdminOrders;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class orderlistActivity extends AppCompatActivity {
    private long presstime;
    private RecyclerView orderlist;
    private DatabaseReference orderref;
    private Toast backtoast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);
        orderref = FirebaseDatabase.getInstance().getReference().child("Orders");
        orderlist = findViewById(R.id.recycleview);
        orderlist.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<AdminOrders> options=
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                        .setQuery(orderref,AdminOrders.class)
                        .build();
        FirebaseRecyclerAdapter<AdminOrders,AdiminOrderViewhold>adapter
                = new FirebaseRecyclerAdapter<AdminOrders, AdiminOrderViewhold>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdiminOrderViewhold holder, int position, @NonNull AdminOrders model) {

                holder.username.setText("Name : "+ model.getName());
                holder.phone.setText("Phone : "+ model.getPhone());
                holder.location.setText("Location : "+ model.getLocation());
                holder.price.setText("Total Price : "+ model.getAmount()+" Baht");
                holder.pickup.setText("Pick Up : "+ model.getReceive());
                holder.viewmoreButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String UID = getRef(position).getKey();
                        Intent intent =new Intent(orderlistActivity.this,info_of_order.class);
                        intent.putExtra("uid",UID);
                        startActivity(intent);

                    }
                });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uid = getRef(position).getKey();
                        if(presstime+2000 > System.currentTimeMillis()){
                            backtoast.cancel();
                            orderref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    orderref.child(uid).removeValue();
                                    Intent intent1 = new Intent(orderlistActivity.this,orderlistActivity.class);
                                    startActivity(intent1);
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                            return;
                        }
                        else{
                            backtoast =  Toast.makeText(orderlistActivity.this, "press again to delete", Toast.LENGTH_SHORT);
                            backtoast.show();
                        }
                        presstime = System.currentTimeMillis();
                    }
                });
            }

            @NonNull
            @Override
            public AdiminOrderViewhold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);
                return new AdiminOrderViewhold(view);
            }
        };
        orderlist.setAdapter(adapter);
        adapter.startListening();
    }

    public static class AdiminOrderViewhold extends RecyclerView.ViewHolder{
        public TextView username,phone,price,location,pickup;
        Button viewmoreButton;
        public AdiminOrderViewhold(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username_lay);
            phone = itemView.findViewById(R.id.phone_lay);
            price = itemView.findViewById(R.id.price_lay);
            location = itemView.findViewById(R.id.location_lay);
            username = itemView.findViewById(R.id.username_lay);
            viewmoreButton = itemView.findViewById(R.id.view_more);
            pickup = itemView.findViewById(R.id.receive_lay);
        }
    }

    public void onBackPressed() {
        if(presstime+2000>System.currentTimeMillis()){
            backtoast.cancel();
            startActivity(new Intent(orderlistActivity.this,MainActivity.class));
            return;
        }
        else{
            backtoast =  Toast.makeText(this, "กดอีกครั้งเพื่อย้อน", Toast.LENGTH_SHORT);
            backtoast.show();
        }
        presstime = System.currentTimeMillis();
    }
}
