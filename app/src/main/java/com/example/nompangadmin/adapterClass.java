package com.example.nompangadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nompangadmin.models.models;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class adapterClass extends FirebaseRecyclerAdapter<models,adapterClass.myviewholder>
{
    public adapterClass(@NonNull FirebaseRecyclerOptions<models> options) {

        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull models model)
    {

        holder.productName.setText(model.getProduct());

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {

        TextView productName,productType;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            productName=(TextView) itemView.findViewById(R.id.productCategory);

        }
    }
}
