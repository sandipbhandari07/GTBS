package clz.project.gtbs;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import clz.project.gtbs.DbModels.Products;
import clz.project.gtbs.feature.feature;

public class ProductDetails extends AppCompatActivity {

    private Button addtocartbtn;
    private ImageView productimage;
    private ElegantNumberButton numberbtn;
    private TextView productprice,productdescription,productname;
    private String productID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.BLACK);
        setContentView(R.layout.activity_product_details);

        productID = getIntent().getStringExtra("pid");

        addtocartbtn=findViewById(R.id.pd_add_to_cart_button);
        numberbtn=findViewById(R.id.number_btn);
        productimage=findViewById(R.id.product_image_details);
        productname=findViewById(R.id.product_name_details);
        productdescription=findViewById(R.id.product_description_details);
        productprice=findViewById(R.id.product_price_details);


        getProductDetails(productID);

        addtocartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingtoCartlist();
            }
        });
    }

    private void addingtoCartlist() {

        String savecurrentTime, savecurrentDate;
        Calendar calFordate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        savecurrentDate = currentDate.format(calFordate.getTime());

        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
        savecurrentTime = currenttime.format(calFordate.getTime());

        final DatabaseReference cartlistRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid",productID);
        cartMap.put("pname",productname.getText().toString());
        cartMap.put("price",productprice.getText().toString());
        cartMap.put("date",savecurrentDate);
        cartMap.put("time",savecurrentTime);
        cartMap.put("quantity",numberbtn.getNumber());
        cartMap.put("discount","");

        cartlistRef.child("User View").child(feature.currentonlineUser.getPhone()).child("Products").child(productID)
                .updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            cartlistRef.child("Admin View").child(feature.currentonlineUser.getPhone()).child("Products").child(productID)
                                    .updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                            {
                                                Toast.makeText(ProductDetails.this, "Added to cart list", Toast.LENGTH_SHORT).show();

                                                Intent intent=new Intent(ProductDetails.this,Homepage.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                        }
                    }
                });


    }

    private void getProductDetails(String productID) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Products products = snapshot.getValue(Products.class);

                    productname.setText(products.getPname());
                    productprice.setText(products.getPrice());
                    productdescription.setText(products.getDescription());
                    Picasso.get().load(products.getImage()).into(productimage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}