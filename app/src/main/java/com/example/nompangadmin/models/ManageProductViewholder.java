package com.example.nompangadmin.models;

import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nompangadmin.R;

public class ManageProductViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView NameProduct;
    public Switch StatusProduct;

    public ManageProductViewholder(@NonNull View itemView) {
        super(itemView);

        NameProduct = itemView.findViewById(R.id.ManageProductName);
        StatusProduct = itemView.findViewById(R.id.ManageProductStatus);
    }
    @Override
    public void onClick(View v) {

    }
}

