package com.xinlan.swipelayoutview;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    private SwipeViewLayout mSwipeLayout;

    private View mMainView;

    private View mBtn1;
    private View mBtn2;
    private View mBtn3;

    private View mOpenLeftBtn;
    private View mOpenRightBtn;
    private View mCloseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwipeLayout = findViewById(R.id.swipe);

        mMainView = findViewById(R.id.mainView);
        mMainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity2.this, " click", Toast.LENGTH_SHORT).show();
            }
        });

        mMainView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity2.this, "long click", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        mBtn1 = findViewById(R.id.btn1);
        mBtn2 = findViewById(R.id.btn2);
        mBtn3 = findViewById(R.id.btn3);


        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity2.this, "button1 click", Toast.LENGTH_SHORT).show();
                mSwipeLayout.closeView();
            }
        });

        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity2.this, "button2 click", Toast.LENGTH_SHORT).show();
                mSwipeLayout.closeView();
            }
        });

        mBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity2.this, "button3 click", Toast.LENGTH_SHORT).show();
                mSwipeLayout.closeView();
            }
        });

        mOpenLeftBtn = findViewById(R.id.openLeft);
        mOpenRightBtn = findViewById(R.id.openRight);
        mCloseBtn = findViewById(R.id.close);

        mOpenLeftBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mSwipeLayout.openLeftView();
            }
        });
        mOpenRightBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mSwipeLayout.openRightView();
            }
        });
        mCloseBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mSwipeLayout.closeView();
            }
        });
    }
}
