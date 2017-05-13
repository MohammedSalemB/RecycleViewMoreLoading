package com.example.capps.cardviewtest;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<String>> {

    @BindView(R.id.cycle)
    RecyclerView recyclerView;
    @BindView(R.id.progress_loading)
    ProgressBar progressBar;

    RecyclerView.Adapter adapter;
    static final String LAST_ITEM = "last_item";
    private List<String> dummyData;
    private boolean isLoaing = false;
    private LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        setFirstDataToRecycle();


        setLisiners();
    }

    private void setLisiners() {
//        recyclerView.setOnScrollChangeListener();

         recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

             int lastItemPosition;
             @Override
             public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                 super.onScrolled(recyclerView, dx, dy);
                 if (!isLoaing){
                     lastItemPosition = layoutManager.findLastVisibleItemPosition();
                     if(lastItemPosition == adapter.getItemCount()-1){
                         getNextData(lastItemPosition);
                     }
                 }


             }
         });
    }

    private void getNextData(int lastItemPosition) {
        Bundle arg = new Bundle();
        arg.putInt(Main2Activity.LAST_ITEM,lastItemPosition);
        getSupportLoaderManager().restartLoader(17,arg,this).forceLoad();
    }



    private void setFirstDataToRecycle() {

        adapter = new Adapter(this, getDummyData());
        recyclerView.setAdapter(adapter);

    }

    private void startLoading(){
        isLoaing = true;
        progressBar.setVisibility(View.VISIBLE);
    }

    private void finishedLoading(){
        isLoaing = false;
        progressBar.setVisibility(View.GONE);
    }

    private List<String> getDummyData() {


        dummyData = Arrays.asList(new String[]{"Item 01", "Item 02", "Item 03", "Item 04", "Item 05", "Item 06", "Item 07", "Item 08"
                , "Item 09", "Item 10", "Item 0", "Item 11", "Item 12", "Item 13", "Item 14", "Item 15", "Item 16",
                "Item 17", "Item 18"});

        dummyData = new ArrayList<String>(dummyData);
        return dummyData;
    }





    //AsyncTaskLoader
    @Override
    public Loader<List<String>> onCreateLoader(int id, Bundle args) {
        startLoading();
        return new MyAsyncTaskLoader(this,args);
    }

    @Override
    public void onLoadFinished(Loader<List<String>> loader, List<String> data) {

        finishedLoading();
        if (data.size() > 0){
            dummyData.addAll(data);
//            adapter.notifyDataSetChanged();
        }else{
            //Notify user No data More
        }



    }

    @Override
    public void onLoaderReset(Loader<List<String>> loader) {

    }


    static class MyAsyncTaskLoader extends AsyncTaskLoader{
        private int lastItem;

        public MyAsyncTaskLoader(Context context,Bundle arg) {
            super(context);
            this.lastItem = arg.getInt(Main2Activity.LAST_ITEM);
        }

        @Override
        public List<String> loadInBackground() {

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return Arrays.asList(nextDummyData(lastItem));
        }

        private String[] nextDummyData(int lastItem) {
            String items[]= new String[10];
            StringBuilder builder = new StringBuilder("Item ");
            for (int i = lastItem; i < (lastItem + 10); i++) {
                items[i - lastItem] = builder.replace(4,builder.length(),i + "").toString();
            }
            return items;
        }
    }
}

