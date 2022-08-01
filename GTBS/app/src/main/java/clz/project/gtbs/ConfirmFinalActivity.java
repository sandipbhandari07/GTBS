package clz.project.gtbs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    }
}