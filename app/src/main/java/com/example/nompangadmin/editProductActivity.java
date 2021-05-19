package com.example.nompangadmin;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nompangadmin.models.ManageProductViewholder;
import com.example.nompangadmin.models.EditProductViewHolder;
import com.example.nompangadmin.models.models;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class editProductActivity extends AppCompatActivity {

    private Button editButton,deleteButton;
    private RecyclerView manage_product;
    public String Test;
    private ImageView editProductImageView;
    private EditText nameProductText;
    private EditText descriptionProductText;
    private EditText priceProductText;
    private Button cancle;
    private Button edit;
    private Button editPictureButton;


    private static final int PICK_IMAGE_REQUEST=1;
    private Uri pictureUri;
    private int positionOfDrink,tempDrink;
    private int sizeOfDrink;
    private String numberOfDrink,prePosition;
    RecyclerView.LayoutManager layoutManager;

    private Toast backtoast;
    private long presstime;

    private UploadTask uploadTask;
    private StorageReference storageRef;
    private DatabaseReference DatabaseDrinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        DatabaseDrinks = FirebaseDatabase.getInstance().getReference().child("Product").child("Drinks");
        storageRef = FirebaseStorage.getInstance().getReference("drink_Picture");


        manage_product = findViewById(R.id.manageStore);
        layoutManager = new LinearLayoutManager(this);
        manage_product.setLayoutManager(layoutManager);
        editButton = findViewById(R.id.editProductbutton);
        deleteButton = findViewById(R.id.deleteProductbutton);

        Calendar cal  = Calendar.getInstance();
        int h = cal.get(Calendar.HOUR_OF_DAY);

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++*-*-*-*-*-*-*-*-*-*-*-*"+h);



    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<models> Drink =
                new FirebaseRecyclerOptions.Builder<models>()
                        .setQuery(DatabaseDrinks, models.class)
                        .build();
        FirebaseRecyclerAdapter<models, EditProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<models, EditProductViewHolder>(Drink) {
                    @Override
                    protected void onBindViewHolder(@NonNull EditProductViewHolder holder, int position, @NonNull models model) {
                        String Position = String.valueOf(position+1);

                        Test = getRef(position).getKey();

                        holder.NameProduct.setText("ชื่อสินค้า : "+model.getName());
                        holder.DescriptionProduct.setText("รายละเอียดสินค้า : "+model.getDescription());
                        holder.PriceProduct.setText("ราคา : "+model.getPrice()+" บาท");

                        holder.deleteProduct.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DatabaseDrinks.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        HashMap<String, Object> replaceProduct = new HashMap<>();

                                        sizeOfDrink = (int) snapshot.getChildrenCount();
                                        numberOfDrink = String.valueOf(sizeOfDrink);

                                        String Description = snapshot.child(numberOfDrink).child("description").getValue().toString();
                                        String imageuri = snapshot.child(numberOfDrink).child("imageuri").getValue().toString();
                                        String name = snapshot.child(numberOfDrink).child("name").getValue().toString();
                                        String price = snapshot.child(numberOfDrink).child("price").getValue().toString();

                                        replaceProduct.put("description",Description);
                                        replaceProduct.put("imageuri",imageuri);
                                        replaceProduct.put("name",name);
                                        replaceProduct.put("price",price);

                                        DatabaseDrinks.child(Position).updateChildren(replaceProduct);
                                        DatabaseDrinks.child(numberOfDrink).removeValue();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        });

                        holder.editProduct.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Dialog dialog = new Dialog(editProductActivity.this);
                                dialog.setTitle("แก้ไขสินค้า");
                                dialog.setContentView(R.layout.dialog);
                                dialog.show();

                                editProductImageView = dialog.findViewById(R.id.editproductimageView);
                                nameProductText = dialog.findViewById(R.id.productNameDialog);
                                descriptionProductText = dialog.findViewById(R.id.productDescriptonDialog);
                                priceProductText = dialog.findViewById(R.id.productPriceDialog);
                                cancle = dialog.findViewById(R.id.button_cancel);
                                edit = dialog.findViewById(R.id.button_edit);
                                editPictureButton = dialog.findViewById(R.id.button_chosePicture);

                                nameProductText.setText(model.getName());
                                descriptionProductText.setText(model.getDescription());
                                priceProductText.setText(model.getPrice());
                                DatabaseDrinks.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.child(Position).child("imageuri").exists())
                                        {
                                            String uriFromStorage = Objects.requireNonNull(snapshot.child(Position).child("imageuri").getValue()).toString();
                                            Picasso.with(editProductActivity.this).load(uriFromStorage).into(editProductImageView);
                                        } else{
                                            storageRef.child("image-not-found.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    Picasso.with(editProductActivity.this).load(uri).into(editProductImageView);
                                                }
                                            });
                                        }



                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                editPictureButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        choosePicture();
                                    }
                                });

                                cancle.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });

                                edit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        DatabaseDrinks.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                HashMap<String, Object> editUpdate = new HashMap<>();
                                                editUpdate.put("name",nameProductText.getText().toString());
                                                editUpdate.put("description",descriptionProductText.getText().toString());
                                                editUpdate.put("price",priceProductText.getText().toString());
                                                DatabaseDrinks.child(Position).updateChildren(editUpdate);


                                                StorageReference fileRef = storageRef.child(Position+"."+getFileExtension(pictureUri));
                                                uploadTask = (UploadTask) fileRef.putFile(pictureUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                    }
                                                });
                                                storageRef.child(Position+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        editUpdate.put("imageuri",uri.toString());
                                                        DatabaseDrinks.child(Position).updateChildren(editUpdate);
                                                    }
                                                });
                                                DatabaseDrinks.child(Position).updateChildren(editUpdate);
                                                dialog.dismiss();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                });

                            }
                        });
                    }
                    @NonNull
                    @Override
                    public EditProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_order_layout, parent, false);
                        return new EditProductViewHolder(view);
                    }
                };
        manage_product.setAdapter(adapter);
        adapter.startListening();
    }

    public void choosePicture()
    {
        Intent getPicture = new Intent();
        getPicture.setType("image/*");
        getPicture.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(getPicture,PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== PICK_IMAGE_REQUEST && data !=null && data.getData() != null)
        {
            pictureUri = data.getData();
            Picasso.with(editProductActivity.this).load(pictureUri).into(editProductImageView);

        }
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void onBackPressed(){
        if(presstime+2000>System.currentTimeMillis()){
            backtoast.cancel();
            startActivity(new Intent(editProductActivity.this, MainActivity.class));
            return;
        }
        else{
            backtoast =  Toast.makeText(this, "กดอีกครั้งเพื่อย้อน", Toast.LENGTH_SHORT);
            backtoast.show();
        }
        presstime = System.currentTimeMillis();
    }
}


