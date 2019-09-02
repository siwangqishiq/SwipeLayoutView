package com.xinlan.swipelayoutview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mains);
        mListView = findViewById(R.id.list_view);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        mListView.setAdapter(new ItemAdapter());
    }

    private final class ItemViewHolder extends RecyclerView.ViewHolder{
        private SwipeViewLayout rootSwipeLayout;
        private View mainView;
        private View btn1;
        private View btn2;
        private View btn3;

        public ItemViewHolder(View itemView) {
            super(itemView);

            rootSwipeLayout = (SwipeViewLayout)itemView;

            mainView = itemView.findViewById(R.id.mainView);


            btn1 = itemView.findViewById(R.id.btn1);
            btn2 = itemView.findViewById(R.id.btn2);
            btn3 = itemView.findViewById(R.id.btn3);
        }
    }

    private final class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder>{

        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final View rootView = LayoutInflater.from(MainActivity.this).inflate(R.layout.view , parent , false);
            return new ItemViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(final ItemViewHolder holder, int position) {
            holder.rootSwipeLayout.closeViewInstant();

            holder.mainView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, " click", Toast.LENGTH_SHORT).show();
                }
            });

            holder.mainView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(MainActivity.this, "long click", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

            holder.btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "button1 click", Toast.LENGTH_SHORT).show();
                    holder.rootSwipeLayout.closeView();
                }
            });

            holder.btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "button2 click", Toast.LENGTH_SHORT).show();
                    holder.rootSwipeLayout.closeView();
                }
            });

            holder.btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "button3 click", Toast.LENGTH_SHORT).show();
                    holder.rootSwipeLayout.closeView();
                }
            });
        }

        @Override
        public int getItemCount() {
            return 1000;
        }
    }
}
