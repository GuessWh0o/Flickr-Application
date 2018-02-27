package com.maks.flickrapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GuessWh0o on 05.01.2017.
 * Email: developerint97@gmail.com
 */

public class MainActivity extends BaseActivity implements GetFlickrJsonData.IOnDataAvailable,
                                RecyclerItemClickListener.IOnRecyclerClickListener {

    private FlickrRecyclerViewAdapter mFlickrRecyclerViewAdapter;

    @Override
    public void onDataAvailable(List<Photo> data, DownloadStatus status) {
        if(status == DownloadStatus.OK){
            mFlickrRecyclerViewAdapter.loadNewData(data);
        } else{
            Log.d(TAG, "onDataAvailable: failed with status " + status);
        }
        Log.d(TAG, "onDataAvailable: ENDS");
    }

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activateToolbar(false);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, this));

        mFlickrRecyclerViewAdapter = new FlickrRecyclerViewAdapter(this, new ArrayList<Photo>());
        recyclerView.setAdapter(mFlickrRecyclerViewAdapter);
        //GetRawData getRawData = new GetRawData(this);
      //  getRawData.execute("https://api.flickr.com/services/feeds/photos_public.gne?tags=android&tagmode=any&format=json&nojsoncallback=1");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: STARTS");
        super.onResume();
        GetFlickrJsonData getFlickJsonData = new GetFlickrJsonData(this, "https://api.flickr.com/services/feeds/photos_public.gne", "en-us", true);
       // getFlickJsonData.executeOnSameThread("android, nougat");
        getFlickJsonData.execute("android, nougat");
        Log.d(TAG, "onPostResume: ENDS");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnItemClick(View view, int position) {
        Log.d(TAG, "OnItemClick: STARTS");
        Toast.makeText(MainActivity.this, "Normal Tap at position " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnItemLongClick(View view, int position) {
        Log.d(TAG, "OnItemLongClick: STARTS");
//        Toast.makeText(MainActivity.this, "Long Tap at position " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, PhotoDetailActivity.class);
        intent.putExtra(PHOTO_TRANSFER, mFlickrRecyclerViewAdapter.getPhoto(position));
        startActivity(intent);
    }

}
