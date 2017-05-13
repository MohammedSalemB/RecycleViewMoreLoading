package com.example.capps.cardviewtest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.cycle)
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        setRecycleViewAdapter();






    }

    private void setRecycleViewAdapter() {
        recyclerView.setAdapter(new Adpter(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    class Adpter extends  RecyclerView.Adapter{

        Context mContext;

        public Adpter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            View view = LayoutInflater.from(mContext).inflate(R.layout.card_view_item,parent,false);
            HOlder hOlder = new HOlder(view);
            return hOlder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            HOlder hOlder = (HOlder) holder;
            hOlder.image.setImageResource(R.drawable.image0);
            hOlder.textView.setText("Items Of Images");

        }

        @Override
        public int getItemCount() {
            return 17;
        }


        class HOlder extends RecyclerView.ViewHolder{

            @BindView(R.id.image)
            ImageView image;
            @BindView(R.id.text)
            TextView textView;



            public HOlder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
            }
        }

    }
}
