package clz.project.gtbs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class admincategory extends AppCompatActivity {
    private ImageView action,adventure,rpg;
    private ImageView racing,strategy,horror;
    private ImageView mobilegames,gaminggiftcards,valorant;
    private ImageView genshinimpact,pubg,freefire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.BLACK);
        setContentView(R.layout.activity_admincategory);

        action=findViewById(R.id.action);
        adventure=findViewById(R.id.adventure);
        rpg=findViewById(R.id.rpg);
        racing=findViewById(R.id.racing);
        strategy=findViewById(R.id.strategy);
        horror=findViewById(R.id.horror);
        mobilegames=findViewById(R.id.mobilegames);
        gaminggiftcards=findViewById(R.id.giftcards);
        valorant=findViewById(R.id.valorant);
        genshinimpact=findViewById(R.id.genshinimpact);
        pubg=findViewById(R.id.pubg);
        freefire=findViewById(R.id.freefire);

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(admincategory.this,AdminAddnewproduct.class);
                intent.putExtra("category","action");
                startActivity(intent);
            }
        });
        adventure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(admincategory.this,AdminAddnewproduct.class);
                intent.putExtra("category","adventure");
                startActivity(intent);
            }
        });
        rpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(admincategory.this,AdminAddnewproduct.class);
                intent.putExtra("category","rpg");
                startActivity(intent);
            }
        });
        racing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(admincategory.this,AdminAddnewproduct.class);
                intent.putExtra("category","racing");
                startActivity(intent);
            }
        });
        strategy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(admincategory.this,AdminAddnewproduct.class);
                intent.putExtra("category","strategy");
                startActivity(intent);
            }
        });
        horror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(admincategory.this,AdminAddnewproduct.class);
                intent.putExtra("category","horror");
                startActivity(intent);
            }
        });
        mobilegames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(admincategory.this,AdminAddnewproduct.class);
                intent.putExtra("category","mobilegames");
                startActivity(intent);
            }
        });
        gaminggiftcards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(admincategory.this,AdminAddnewproduct.class);
                intent.putExtra("category","gaminggiftcards");
                startActivity(intent);
            }
        });
        valorant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(admincategory.this,AdminAddnewproduct.class);
                intent.putExtra("category","valorant");
                startActivity(intent);
            }
        });
        freefire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(admincategory.this,AdminAddnewproduct.class);
                intent.putExtra("category","freefire");
                startActivity(intent);
            }
        });
        pubg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(admincategory.this,AdminAddnewproduct.class);
                intent.putExtra("category","pubg");
                startActivity(intent);
            }
        });
        genshinimpact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(admincategory.this,AdminAddnewproduct.class);
                intent.putExtra("category","genshinimpact");
                startActivity(intent);
            }
        });

    }
}