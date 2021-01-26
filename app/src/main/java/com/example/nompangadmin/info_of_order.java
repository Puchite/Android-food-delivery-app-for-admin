package com.example.nompangadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nompangadmin.models.Cart;
import com.example.nompangadmin.models.viewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class info_of_order extends AppCompatActivity {
    private RecyclerView productlist;
    RecyclerView.LayoutManager layoutManager;
    private  String  usernameid ="";
    private DatabaseReference productreference1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_of_order);
        usernameid = getIntent().getStringExtra("uid");

        productreference1 = FirebaseDatabase.getInstance().getReference().child("Orders").child(usernameid).child("product");

        productlist = findViewById(R.id.recycleview_2);
        productlist.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        productlist.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();
       FirebaseRecyclerOptions<Cart> options =
               new FirebaseRecyclerOptions.Builder<Cart>()
               .setQuery(productreference1,Cart.class)
               .build();
       FirebaseRecyclerAdapter<Cart,viewholder>adapter = new FirebaseRecyclerAdapter<Cart, viewholder>(options) {
           @Override
           protected void onBindViewHolder(@NonNull viewholder holder, int position, @NonNull Cart model) {
                holder.Usernametext.setText("id : "+usernameid);
                holder.AmountText.setText("amount : "+model.getAmount());
                holder.PriceText.setText("price : "+ (Integer.parseInt(model.getPrice())*Integer.parseInt(model.getAmount()))+" baht");
                holder.productText.setText("product name : "+model.getName());

           }

           @NonNull
           @Override
           public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

               View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_orde, parent, false);

               viewholder  holder = new viewholder(view);
               return  holder;
           }
       };
       productlist.setAdapter(adapter);
       adapter.startListening();
    }
}