package clz.project.gtbs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import clz.project.gtbs.CartViewHolder.CartViewHolder;
import clz.project.gtbs.DbModels.Cart;
import clz.project.gtbs.feature.feature;

public class CartAct extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button Nextprocessbtn;
    private TextView txtTotalAmmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.BLACK);
        setContentView(R.layout.activity_cart2);
        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Nextprocessbtn = findViewById(R.id.next_process_btn);
        txtTotalAmmount = findViewById(R.id.total_price);


    }

    @Override
    protected void onStart() {
        super.onStart();
        final DatabaseReference cartListref = FirebaseDatabase.getInstance().getReference().child("Cart List");

        FirebaseRecyclerOptions<Cart> options= new FirebaseRecyclerOptions.Builder<Cart>().setQuery(cartListref.child("User View")
                .child(feature.currentonlineUser.getPhone()).child("Products"),Cart.class).build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter =
                new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {

                        holder.txtproductquantity.setText("Quantity = "+model.getQuantity());
                        holder.txtproductprice.setText("Price ="+model.getPrice()+ "");
                        holder.txtproductname.setText("Prodcutname = "+model.getPname());

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[] = new CharSequence[]
                                        {
                                          "Edit",
                                          "Remove"
                                        };
                                AlertDialog.Builder builder=new AlertDialog.Builder(CartAct.this);
                                builder.setTitle("Cart Options:");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        if (i==0)
                                        {
                                            Intent intent=new Intent(CartAct.this,ProductDetails.class);
                                            intent.putExtra("pid",model.getPid());
                                            startActivity(intent);
                                        }
                                        if (i==1)
                                        {
                                            cartListref.child("User View")
                                                    .child(feature.currentonlineUser.getPhone()).child("Products")
                                                    .child(model.getPid())
                                                    .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful())
                                                            {
                                                                Toast.makeText(CartAct.this, "Item removed", Toast.LENGTH_SHORT).show();
                                                                Intent intent=new Intent(CartAct.this,Homepage.class);
                                                                startActivity(intent);

                                                            }
                                                        }
                                                    });
                                        }


                                    }
                                });
                                builder.show();
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                        CartViewHolder holder=new CartViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}