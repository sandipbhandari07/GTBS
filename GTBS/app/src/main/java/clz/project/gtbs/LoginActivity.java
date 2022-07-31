package clz.project.gtbs;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.Feature;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import clz.project.gtbs.DbModels.Users;
import clz.project.gtbs.feature.feature;
import io.paperdb.Paper;


public class LoginActivity extends AppCompatActivity {

    private EditText txtphnnumber,txtpassword;
    private Button Loginbtn;
    private ProgressDialog loadingBarP;

    private String parentDbName= "Users";
    private CheckBox chkboxrememberme;

    private TextView adminpannel, notadminpannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.BLACK);
        setContentView(R.layout.activity_login);

        txtphnnumber=findViewById(R.id.login_phnnumber);
        txtpassword=findViewById(R.id.login_password);
        Loginbtn=findViewById(R.id.login_login);
        adminpannel=findViewById(R.id.i_am_admin);
        notadminpannel=findViewById(R.id.not_admin_panel_link);
        loadingBarP= new ProgressDialog(this);

        chkboxrememberme=findViewById(R.id.remember_me_cb);
        Paper.init(this);

        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        adminpannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loginbtn.setText("Login Admin");
                adminpannel.setVisibility(View.INVISIBLE);
                notadminpannel.setVisibility(View.VISIBLE);
                parentDbName="Admins";
            }
        });

        notadminpannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loginbtn.setText("Login");
                adminpannel.setVisibility(View.VISIBLE);
                notadminpannel.setVisibility(View.INVISIBLE);
                parentDbName="Users";
            }
        });
    }


    private void loginUser() {
        String phone=txtphnnumber.getText().toString();
        String password = txtpassword.getText().toString();

        if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this,"Please write your phone number...",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Please write your password...",Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBarP.setTitle("Login Account");
            loadingBarP.setMessage("Please wait,while we are checking the credentials");
            loadingBarP.setCanceledOnTouchOutside(false);
            loadingBarP.show();

            AllowAccessToAccount(phone,password);
        }
    }

    private void AllowAccessToAccount(String phone, String password) {

        if (chkboxrememberme.isChecked())
        {
            Paper.book().write(feature.UserPhoneKey,phone);
            Paper.book().write(feature.UserPasswordkey,password);
        }


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                
                if (snapshot.child(parentDbName).child(phone).exists())
                {
                    Users usersData=snapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                            if (parentDbName.equals("Admins"))
                            {
                                Toast.makeText(LoginActivity.this, "Welcome admin, you logged successfully...", Toast.LENGTH_SHORT).show();
                                loadingBarP.dismiss();

                                Intent intent=new Intent(LoginActivity.this,admincategory.class);
                                startActivity(intent);
                            }
                            else if (parentDbName.equals("Users"))
                            {
                                Toast.makeText(LoginActivity.this, "Logged in Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBarP.dismiss();

                                Intent intent=new Intent(LoginActivity.this,Homepage.class);
                                //current user name to call in homepage
                                feature.currentonlineUser=usersData;
                                startActivity(intent);
                            }
                        }
                        else
                        {
                            loadingBarP.dismiss();
                            Toast.makeText(LoginActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Account with this "+ phone + " number do not exists.", Toast.LENGTH_SHORT).show();
                    loadingBarP.dismiss();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}