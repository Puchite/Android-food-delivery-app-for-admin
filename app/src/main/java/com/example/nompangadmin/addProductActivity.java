package com.example.nompangadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nompangadmin.models.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;


import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.io.FileFilter;
import com.example.nompangadmin.Prevalent.*;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class addProductActivity extends AppCompatActivity {

    private ContactsContract.CommonDataKinds.Email email;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button addProductButton,addPictureButton;
    private ImageView inputProductImage;
    private ProgressBar progressBar;
    private Uri pictureUri,imageUri;
    private FileDownloadTask recentUri;
    private EditText inputProductName, inputProductDiscription, inputProductPrice;
    private TextView fileName;
    private String productName, productDiscription, productPrice,productList;
    private long presstime;
    private Toast backtoast;
    private int productNumber;
    private DatabaseReference productRef, root, rootDrink;
    private StorageTask uploadTask,dowloadTask;
    private String productImageURI;
    private int sizeOfDrink;
    private boolean checkIsString;
    private FirebaseAuth mAuth;
    private static boolean checkUploaded;
    private String recentFileName;
    private String adminEmail,adminPassword;
    private StorageReference storageRef,storageRef2;
    private DatabaseReference dataRef;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);

        addProductButton = findViewById(R.id.button_addproduct);
        addPictureButton = findViewById(R.id.button_addpicture);
        inputProductImage = findViewById(R.id.product_image);
        inputProductName = findViewById(R.id.product_name);
        inputProductDiscription = findViewById(R.id.product_discription);
        inputProductPrice = findViewById(R.id.product_price);
        progressBar = findViewById(R.id.progressBar);
        fileName = findViewById(R.id.fileName);

        storageRef = FirebaseStorage.getInstance().getReference("drink_Picture");
        dataRef = FirebaseDatabase.getInstance().getReference("drink_Picture");

        adminEmail = "0880880880@gmail.com";
        adminPassword = "0880880880";
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(adminEmail,adminPassword);

        productRef = FirebaseDatabase.getInstance().getReference().child("Product").child("Drinks");
        root = FirebaseDatabase.getInstance().getReference();


        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        checkIsString = isNumeric(inputProductPrice.getText().toString());
                        if(uploadTask != null && uploadTask.isInProgress())
                        {
                            Toast.makeText(addProductActivity.this, "กำลังเพิ่มสินค้า", Toast.LENGTH_SHORT).show();
                        }
                        if(checkIsString)
                        {
                            Toast.makeText(addProductActivity.this, "กรุณากรอกข้อมูลให้ถูกต้อง", Toast.LENGTH_SHORT).show();
                        }
                        if (inputProductName.getText().toString().isEmpty() || inputProductDiscription.getText().toString().isEmpty() || inputProductPrice.getText().toString().isEmpty())
                        {
                            Toast.makeText(addProductActivity.this, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();
                        } else {
                            checkUploaded = true;
                            uploadPicture();

                            if(checkUploaded)
                            {
                                HashMap<String, Object> productMap = new HashMap<>();
                                sizeOfDrink = (int) snapshot.getChildrenCount();
                                sizeOfDrink++;
                                String numberOfDrinks = String.valueOf(sizeOfDrink);
                                storageRef.child(numberOfDrinks+"."+getFileExtension(pictureUri)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        HashMap<String, Object> imagePut = new HashMap<>();
                                        imagePut.put("imageuri",uri.toString());
                                        productMap.put("description", inputProductDiscription.getText().toString());
                                        productMap.put("name", inputProductName.getText().toString());
                                        productMap.put("price", inputProductPrice.getText().toString());
                                        productMap.put("status", "stock");

                                        root.child("Product").child("Drinks").child(numberOfDrinks).updateChildren(productMap);
                                        root.child("Product").child("Drinks").child(numberOfDrinks).updateChildren(imagePut);

                                        Toast.makeText(addProductActivity.this, "สำเร็จ", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(addProductActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(addProductActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        addPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                choosePicture();
            }
        });
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
            Picasso.with(this).load(pictureUri).into(inputProductImage);
        }
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public boolean checkFile(File file) {
            for (String extension: acceptedFile){
                if(file.getName().toLowerCase().endsWith(extension)){
                    return true;
                }
            }
            return false;
    };

    private final String[] acceptedFile = new String[]{
            "jpg","png","jpeg"
    };

    private void uploadPicture()
    {
        if(pictureUri != null)
        {
            rootDrink = FirebaseDatabase.getInstance().getReference().child("Product").child("Drinks");
            rootDrink.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    productNumber = (int) snapshot.getChildrenCount();
                    productNumber++;
                    productList = String.valueOf(productNumber);
                    System.out.println(productNumber);

                    StorageReference fileReference = storageRef.child(productList+ "." + getFileExtension(pictureUri));
                    uploadTask = fileReference.putFile(pictureUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressBar.setProgress(0);
                                        }
                                    }, 5000);

                                    uploadPictureModels upload = new uploadPictureModels(fileName.getText().toString().trim(), Objects.requireNonNull(taskSnapshot.getUploadSessionUri()).toString());
                                    String uploadID = dataRef.push().getKey();
                                    assert uploadID != null;
                                    dataRef.child(uploadID).setValue(upload);

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(addProductActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                    double progress = (100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                                    progressBar.setProgress((int)progress);
                                }
                            });
                    
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else {
            Toast.makeText(addProductActivity.this, "ไม่มีไฟล์ที่เลือก", Toast.LENGTH_SHORT).show();
        }
    }



    public void onBackPressed() {

        if (presstime + 2000 > System.currentTimeMillis()) {
            backtoast.cancel();
            startActivity(new Intent(addProductActivity.this, MainActivity.class));
            return;
        } else {

            backtoast = Toast.makeText(this, "กดอีกครั้งเพื่อออก", Toast.LENGTH_SHORT);
            backtoast.show();
        }
        presstime = System.currentTimeMillis();
    }

    public static boolean isNumeric(String string) {
        int intValue;

        try {
            intValue = Integer.parseInt(string);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

}

