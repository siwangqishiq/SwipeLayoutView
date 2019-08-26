package com.xinlan.swipelayoutview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SwipeMenuLayout mSwipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeLayout = findViewById(R.id.swipe);
        //mSwipeLayout.setMainView(R.layout.view);

        mSwipeLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this , " click" , Toast.LENGTH_SHORT).show();
            }
        });

        mSwipeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this , "long click" , Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
