package com.example.nompangadmin.models;

import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nompangadmin.R;

public class EditProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView NameProduct, DescriptionProduct, PriceProduct;
    public Button deleteProduct,editProduct;

    public EditProductViewHolder(@NonNull View itemView) {
        super(itemView);

        NameProduct = itemView.findViewById(R.id.ManageProductName);
        deleteProduct = itemView.findViewById(R.id.deleteProductbutton);
        editProduct = itemView.findViewById(R.id.editProductbutton);
        DescriptionProduct = itemView.findViewById(R.id.ManageProductDescription);
        PriceProduct = itemView.findViewById(R.id.ManageProductPrice);
    }
    @Override
    public void onClick(View v) {

    }
}

