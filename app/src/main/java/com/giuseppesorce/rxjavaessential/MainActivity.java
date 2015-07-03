package com.giuseppesorce.rxjavaessential;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Observer;

public class MainActivity extends AppCompatActivity {

    private String mFilesDir;

    @InjectView(R.id.so_recyclerview)
    RecyclerView mRecyclerView;

    @InjectView(R.id.so_swipe)
    SwipeRefreshLayout mSwipe;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mSwipe.setOnRefreshListener(this::refreshTheList);
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

    private Observable<AppInfo> getApps() {

        Observable<AppInfo> appsX =null;
        appsX= Observable.create(subscriber -> {
            List<AppInfoRich>  apps= new ArrayList<AppInfoRich>();
            final Intent mainIntent= new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            List<ResolveInfo> infos= getPackageManager().queryIntentActivities(mainIntent, 0);
            for (ResolveInfo info: infos){
                apps.add(new AppInfoRich(this, info));
            }
            for (AppInfoRich appInfo : apps) {
                Bitmap icon =
                        Utils.drawableToBitmap(appInfo.getIcon());

                String name= appInfo.getName();
                String iconPath = mFilesDir + "/" + name;
                Utils.storeBitmap(App.instance, icon, name);
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                subscriber.onNext(new AppInfo(name, iconPath,
                        appInfo.getLastUpdateTime()));
            }
            if (!subscriber.isUnsubscribed()) {
                subscriber.onCompleted();
            }
        });


        return appsX;
    }

    private void refreshTheList(){
        getApps().toSortedList().subscribe(new Observer<List<AppInfo>>() {
            @Override
            public void onCompleted() {
                Toast.makeText(MainActivity.this, "Here is the  list!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, "Something went   wrong!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNext(List<AppInfo> appInfos) {
            Log.i("giuseppesorce", "appInfos: "+appInfos.toString());
            }
        });
    }
}
