package com.example.nompangadmin.models;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nompangadmin.R;

public class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
public TextView Usernametext,PriceText,AmountText,productText;
    public viewholder(@NonNull View itemView) {
        super(itemView);
        Usernametext = itemView.findViewById(R.id.username_lay2);
        PriceText = itemView.findViewById(R.id.price_lay2);
        AmountText = itemView.findViewById(R.id.amount_lay);
        productText = itemView.findViewById(R.id.product_name_lay);
    }

    @Override
    public void onClick(View v) {

    }
}
