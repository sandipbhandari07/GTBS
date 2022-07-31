package clz.project.gtbs;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import clz.project.gtbs.DbModels.Users;
import clz.project.gtbs.feature.feature;
import io.paperdb.Paper;


public class IntroHome extends AppCompatActivity {
    Button login,joinin;
    private ProgressDialog loadingBarP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.BLACK);
        setContentView(R.layout.activity_intro_home);

        login=findViewById(R.id.main_login_btn);
        joinin=findViewById(R.id.join);
        loadingBarP= new ProgressDialog(this);

        Paper.init(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(IntroHome.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        joinin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(IntroHome.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        String userphonekey= Paper.book().read(feature.UserPhoneKey);
        String userpasswordkey= Paper.book().read(feature.UserPasswordkey);
        if (userphonekey != "" && userpasswordkey!= "")
        {
            if (!TextUtils.isEmpty(userphonekey)&& !TextUtils.isEmpty(userpasswordkey))
            {
                allowaccess(userphonekey,userpasswordkey);
                loadingBarP.setTitle("Already Logged in");
                loadingBarP.setMessage("Please wait...");
                loadingBarP.setCanceledOnTouchOutside(false);
                loadingBarP.show();
            }
        }
    }

    private void allowaccess(final String phone, final String password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child("Users").child(phone).exists())
                {
                    Users usersData=snapshot.child("Users").child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                            Toast.makeText(IntroHome.this, "PLease wait, you are already logged in...", Toast.LENGTH_SHORT).show();
                            loadingBarP.dismiss();

                            Intent intent=new Intent(IntroHome.this,Homepage.class);
                            feature.currentonlineUser = usersData;
                            startActivity(intent);
                        }
                        else
                        {
                            loadingBarP.dismiss();
                            Toast.makeText(IntroHome.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                else
                {
                    Toast.makeText(IntroHome.this, "Account with this "+ phone + " number do not exists.", Toast.LENGTH_SHORT).show();
                    loadingBarP.dismiss();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}