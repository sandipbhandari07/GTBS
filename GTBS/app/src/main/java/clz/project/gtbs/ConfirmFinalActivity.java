package clz.project.gtbs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import clz.project.gtbs.feature.feature;

public class ConfirmFinalActivity extends AppCompatActivity {

    private EditText nameedittext, phoneedittext, address1edittext,address2edittext;
    private Button confirmorderbutton;

    private String totalAmount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final);

        totalAmount=getIntent().getStringExtra("Total Price");
        Toast.makeText(this, "Total price = " + totalAmount, Toast.LENGTH_SHORT).show();

        confirmorderbutton=findViewById(R.id.confirm_final_order_btn);
        nameedittext=findViewById(R.id.shippment_name);
        phoneedittext=findViewById(R.id.shippment_phone_number);
        address1edittext=findViewById(R.id.shippment_address1);
        address2edittext=findViewById(R.id.shippment_address2);

        confirmorderbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });
    }

    private void check() {
        if (TextUtils.isEmpty(nameedittext.getText().toString()))
        {
            Toast.makeText(this, "please provide your full name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phoneedittext.getText().toString()))
        {
            Toast.makeText(this, "Please provide phone number", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phoneedittext.getText().toString()))
        {
            Toast.makeText(this, "Please provide address 1", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phoneedittext.getText().toString()))
        {
            Toast.makeText(this, "Please provide address 2", Toast.LENGTH_SHORT).show();
        }
        else {
            confirmorder();
        }

    }

    private void confirmorder() {
        final String savecurrentTime,savecurrentDate;

        Calendar calFordate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        savecurrentDate = currentDate.format(calFordate.getTime());

        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
        savecurrentTime = currenttime.format(calFordate.getTime());

        final DatabaseReference ordersref= FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(feature.currentonlineUser.getPhone());
        HashMap<String,Object> orderhashmap = new HashMap<>();
        orderhashmap.put("TotalAmount",totalAmount);
        orderhashmap.put("name",nameedittext.getText().toString());
        orderhashmap.put("phone",phoneedittext.getText().toString());
        orderhashmap.put("address1",address1edittext.getText().toString());
        orderhashmap.put("address2",address2edittext.getText().toString());
        orderhashmap.put("date",savecurrentDate);
        orderhashmap.put("time",savecurrentTime);
        orderhashmap.put("state","not shipped");

        ordersref.updateChildren(orderhashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    FirebaseDatabase.getInstance().getReference()
                            .child("Cart List")
                            .child("User View")
                            .child(feature.currentonlineUser.getPhone())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(ConfirmFinalActivity.this, "your final order has been placed successfully.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });

    }
}