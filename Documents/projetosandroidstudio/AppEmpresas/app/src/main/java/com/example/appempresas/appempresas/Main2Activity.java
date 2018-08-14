package com.example.appempresas.appempresas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Main2Activity extends AppCompatActivity {

    private TextView name1;
    private ImageView img1;
    private TextView info1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main2);

        name1 = (TextView) findViewById(R.id.text_text12);
        img1 = (ImageView) findViewById(R.id.imageView31);
        info1 = (TextView) findViewById(R.id.textInforma);

        Bundle extra = getIntent().getExtras();
        String loca = extra.getString("loca");
        name1.setText(loca);
        String im = extra.getString("im");
        Glide.with(Main2Activity.this).load(im).into(img1);
        String inf = extra.getString("inf");
        info1.setText(inf);

    }
}
