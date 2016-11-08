package au.com.lumox.angeleyecontroller;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MixerFragment.ValuesChanged, TouchFragment.TouchValuesChanged{

    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    public boolean WifiToggle = false;
    android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;



    SerialFunctions messageService;
    boolean isBound = false;

    ServiceConnection myConnection  = new ServiceConnection() {

        public void onServiceConnected(ComponentName className,
                IBinder service) {
            SerialFunctions.MyLocalBinder binder = (SerialFunctions.MyLocalBinder) service;
            messageService = binder.getService();
            isBound = true;
        }

    public void onServiceDisconnected(ComponentName arg0) {
        isBound = false;
    }

};





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        setupToolbar();


        final Intent intent = new Intent(this, SerialFunctions.class);

        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);


        DataModel[] drawerItem = new DataModel[4];

        drawerItem[0] = new DataModel(R.drawable.connect, "Touch Changer");
        drawerItem[1] = new DataModel(R.drawable.table, "Color Mixer");
        drawerItem[2] = new DataModel(R.drawable.fixtures, "Modes");
        drawerItem[3] = new DataModel(R.drawable.table, "Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerToggle();





        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

    @Override
    public void SendMessage(final String messageToSend){

        new Thread(new Runnable() {
            public void run() {
                messageService.transmitMessage(messageToSend);
            }
        }).start();

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }

    private void selectItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new TouchFragment();
                break;
            case 1:
                fragment = new MixerFragment();
                break;
            case 2:
                fragment = new ModesFragment();
                break;
            case 3:
                fragment = new SettingsFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
            //fragmentManager.beginTransaction().add(fragment,"Fragment").addToBackStack(null).commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.action_settings:

                WifiManager wifiManager = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);

                if (!WifiToggle) {

                    //startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
                    Toast.makeText(getApplicationContext(), "Connect to Headlights.", Toast.LENGTH_SHORT).show();

                    SharedPreferences prefs = getSharedPreferences("WifiPrefs", MODE_PRIVATE);
                    String SSID = prefs.getString("SSIDKey", "BMW");
                    //String wPass = prefs.getString("PassKey", null);


                    WifiConfiguration conf = new WifiConfiguration();
                    conf.SSID = "\"" + SSID + "\"";

                    conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

                    wifiManager.addNetwork(conf);

                    List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
                    for( WifiConfiguration i : list ) {
                        if(i.SSID != null && i.SSID.equals("\"" + SSID + "\"")) {
                            wifiManager.disconnect();
                            wifiManager.enableNetwork(i.networkId, true);
                            wifiManager.reconnect();

                            break;
                        }
                    }





                    WifiToggle = !WifiToggle;


                } else {
                    Toast.makeText(getApplicationContext(), "Disconnecting.", Toast.LENGTH_SHORT).show();
                    //Disconnect from Hardware



                    wifiManager.disconnect(); // Disconnect From Wifi

                    WifiToggle = !WifiToggle;


                }

            default:
                if (mDrawerToggle.onOptionsItemSelected(item)) {
                    return true;
                }
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }


    void setupDrawerToggle(){
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();
    }









}
