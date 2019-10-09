package com.justmovie.app.justmoviesinopsisfilm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

public class main_menu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //deklarasi Interstitial ads
    private InterstitialAd mInterstitialAd;
    private boolean loadingIklan=true;
    private Integer hitung=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        loadfragment(new frag_top_weekly());

        //admob banner
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new FirebaseNotificationOpenedHandler(this))
                .init();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.nav_cari) {
            fragment = new frag_cari();
            loadInterstitial();
        } else if (id == R.id.nav_genre) {
            loadInterstitial();
            fragment = new frag_genre();
        } else if (id == R.id.nav_negara) {
            loadInterstitial();
            fragment = new frag_negara();
        } else if (id == R.id.nav_tahun) {
            loadInterstitial();
            fragment = new frag_tahun();
        } else if (id == R.id.nav_popular) {
            loadInterstitial();
            fragment = new frag_top_weekly();
        } else if (id == R.id.nav_tentang) {
            Intent tampil = new Intent(main_menu.this, menu_tentang.class);
            startActivity(tampil);

        } else if (id == R.id.nav_bagikan) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Jangan mau ketinggalan info terbaru seputar film yg akan tayang dibioskop, yuk download aplikasinya di https://bit.ly/2BC89Vk");

            try {
                startActivity(Intent.createChooser(intent, "Pilih Aplikasi"));
            } catch (android.content.ActivityNotFoundException ex) {
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return loadfragment(fragment);
    }


    private boolean loadfragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentdashboard, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    //Fungsi Interstitial
    public void loadInterstitial() {
        hitung++;
        if (loadingIklan){
            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId(getString(R.string.interstisial_1));
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
            loadingIklan=false;
        }
        if (hitung%5==0){
            if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
                loadingIklan=true;
            }
        }
    }


    public class FirebaseNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        Context ctx;
        FirebaseNotificationOpenedHandler(Context context) {
            ctx = context;
        }
        // This fires when a notification is opened by tapping on it.
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            OSNotificationAction.ActionType actionType = result.action.type;
            JSONObject data = result.notification.payload.additionalData;
            if (data != null) {
                String customKey = data.optString("customkey", null);
                String lagikey = data.optString("lagikey", null);
                if (customKey != null)
                    Log.i("OneSignalExample", "customkey set with value: " + customKey);
                if (lagikey != null)
                    Log.i("OneSignalExample", "lagikey set with value: " + lagikey);
            }
            if (actionType == OSNotificationAction.ActionType.ActionTaken)
                Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID);

            Uri uri = Uri.parse("https://bit.ly/2BC89Vk");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            ctx.startActivity(intent);

          //  Intent intent = new Intent(getApplicationContext(), main_menu.class);
          //  intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
          //  ctx.startActivity(intent);
        }
    }


}
