package com.example.bottomtab.etc;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bottomtab.R;
import com.example.bottomtab.bluetooth.BluetoothLeService;
import com.example.bottomtab.bluetooth.DeviceControlActivity;
import com.example.bottomtab.bluetooth.DeviceScanActivity;
import com.example.bottomtab.bluetooth.SampleGattAttributes;
import com.example.bottomtab.statistics.StatisticsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView drawerNavigationView;

    BottomNavigationView bottomNavigationView;

    ContentsFragment contentsFragment;
    HomeFragment homeFragment;
    StatisticsFragment statisticsFragment;

    FragmentManager fragmentManager;

    OptionDialog optionDialog;

    BluetoothAdapter mBluetoothAdapter;

    final static int BT_REQUEST_ENABLE = 1;
    final static int BT_MESSAGE_READ = 2;
    final static int BT_SCAN = 4;

    public String user_id;

    String receivedData;

    private BluetoothLeService mBluetoothLeService;
    private String mDeviceAddress;
    private boolean mConnected = false;
    public ExpandableListView mGattServicesList;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics = new ArrayList<>();
    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";
    private BluetoothGattCharacteristic mNotifyCharacteristic;

    private boolean stop;
    int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_drawer);
        makeDrawerNavigation();
        optionDialog = new OptionDialog(this);

        contentsFragment = new ContentsFragment();
        homeFragment = new HomeFragment(this);
        statisticsFragment = new StatisticsFragment(this);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, contentsFragment);
        fragmentTransaction.add(R.id.fragment_container, homeFragment);
        fragmentTransaction.add(R.id.fragment_container, statisticsFragment);
        fragmentTransaction.replace(R.id.fragment_container, homeFragment).commit(); // 실행하면 가장 먼저 보이는 화면
        setTitle(R.string.title_home);

        addBottomTabEvents();

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // LoginActivity로부터 로그인 시 유저 아이디를 받아옴
        Bundle extras = getIntent().getExtras();
        user_id = extras.getString("user_id");

        final Intent intent = getIntent();
        mDeviceAddress = intent.getStringExtra("DEVICE_ADDRESS");

        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);

        stop = true;
        time = 0;

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                countTime();
            }
        };

        class TimeRunnable implements Runnable {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace() ;
                    }
                    runOnUiThread(runnable) ;
                }
            }
        }

        TimeRunnable timeRunnable = new TimeRunnable() ;
        Thread thread = new Thread(timeRunnable) ;
        thread.start();
    }

    private void addBottomTabEvents() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setSelectedItemId(R.id.home_menu);
        findViewById(R.id.contents_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationView.setSelectedItemId(R.id.contents_menu);
                setTitle(R.string.title_contents);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, contentsFragment).commit();
            }
        });
        findViewById(R.id.home_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationView.setSelectedItemId(R.id.home_menu);
                setTitle(R.string.title_home);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
            }
        });
        findViewById(R.id.statistics_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationView.setSelectedItemId(R.id.statistics_menu);
                setTitle(R.string.title_statistics);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, statisticsFragment).commit();
                statisticsFragment.user_id = user_id;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        if (id == R.id.bluetooth_menu) {
            if(mBluetoothAdapter.isEnabled()) bluetoothOff();
            else bluetoothOn();
            return true;
        }
        if (id == R.id.option_menu) {
            optionDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() { // close navigation automatically when 'back' is pressed
        // TODO Auto-generated method stub
        if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
        else{
            super.onBackPressed();
        }
    }

    private void makeDrawerNavigation(){
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout_id);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerNavigationView = (NavigationView)findViewById(R.id.navigation_view);
        drawerNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.profile_menu:
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        break;
                    case R.id.device_information_menu:
                        startActivity(new Intent(MainActivity.this, DeviceInformationActivity.class));
                        break;
                    case R.id.instruction_menu:
                        startActivity(new Intent(MainActivity.this, InstructionActivity.class));
                        break;
                    case R.id.customer_service_menu:
                        startActivity(new Intent(MainActivity.this, CustomerServiceActivity.class));
                        break;
                    case R.id.friend_menu:
                        Intent intent = new Intent(MainActivity.this, FriendActivity.class);
                        intent.putExtra("user_id", user_id);
                        startActivity(intent);
                        break;
                    case R.id.log_out_menu:
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                        break;
                    default:
                        return true;
                }
                drawerLayout.closeDrawer(Gravity.LEFT); // close navigation automatically when a menue is clicked
                return true;
            }
        });
    }

    private void makeToast(String string){
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
    }

    public void startReceive(){
        stop = false;
    }

    public void stopReceive() {
        this.stop = true;
        time = 0;
    }

    public void countTime(){
        if(stop) return;
        this.time++;
        int min = time/60;
        int sec = time%60;
        String time = cvt1to2(min)+":"+cvt1to2(sec);
        homeFragment.timeText.setText(time);
    }

    public String cvt1to2(int time){
        if(time<10) return "0"+time;
        else return String.valueOf(time);
    }

    void bluetoothOn() {
        if(mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "블루투스를 지원하지 않는 기기입니다.", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intentBluetoothEnable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intentBluetoothEnable, BT_REQUEST_ENABLE);
        }
    }

    void bluetoothOff() {
        if (mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();
            Toast.makeText(getApplicationContext(), "블루투스가 비활성화 되었습니다.", Toast.LENGTH_SHORT).show();

            homeFragment.setState0();
            homeFragment.receiveThread.stopReceive();
        }
        else {
//            Toast.makeText(getApplicationContext(), "블루투스가 이미 비활성화 되어 있습니다.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case BT_REQUEST_ENABLE:
                if (resultCode == RESULT_OK) { // 블루투스 활성화를 확인을 클릭하였다면
                    Toast.makeText(getApplicationContext(), "블루투스가 활성화 되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, DeviceScanActivity.class);
                    startActivityForResult(intent, BT_SCAN);
                }
                else if (resultCode == RESULT_CANCELED) { // 블루투스 활성화를 취소를 클릭하였다면
                    homeFragment.setState0();
                }
                break;
            case BT_SCAN:
                if(resultCode==0){
                    try {
                        makeToast("기기와 연결되었습니다");
                        mDeviceAddress = data.getStringExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS);
                    }catch (Exception e){
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                finish();
            }
            mBluetoothLeService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    private void updateConnectionState(final int resourceId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //mConnectionState.setText(resourceId);
            }
        });
    }

    private void clearUI() {
        mGattServicesList.setAdapter((SimpleExpandableListAdapter) null);
        receivedData = "no data";
    }

    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
        String uuid = null;
        String unknownServiceString = getResources().getString(R.string.unknown_service);
        String unknownCharaString = getResources().getString(R.string.unknown_characteristic);
        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData
                = new ArrayList<ArrayList<HashMap<String, String>>>();
        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

        BluetoothGattService gattService = gattServices.get(3);

        HashMap<String, String> currentServiceData = new HashMap<String, String>();
        uuid = gattService.getUuid().toString();
        currentServiceData.put(
                LIST_NAME, SampleGattAttributes.lookup(uuid, unknownServiceString));
        currentServiceData.put(LIST_UUID, uuid);
        gattServiceData.add(currentServiceData);

        ArrayList<HashMap<String, String>> gattCharacteristicGroupData =
                new ArrayList<HashMap<String, String>>();
        List<BluetoothGattCharacteristic> gattCharacteristics =
                gattService.getCharacteristics();
        ArrayList<BluetoothGattCharacteristic> charas =
                new ArrayList<BluetoothGattCharacteristic>();

        // Loops through available Characteristics.
        BluetoothGattCharacteristic gattCharacteristic = gattCharacteristics.get(0);
        charas.add(gattCharacteristic);
        HashMap<String, String> currentCharaData = new HashMap<String, String>();
        uuid = gattCharacteristic.getUuid().toString();
        currentCharaData.put(
                LIST_NAME, SampleGattAttributes.lookup(uuid, unknownCharaString));
        currentCharaData.put(LIST_UUID, uuid);
        gattCharacteristicGroupData.add(currentCharaData);

        mGattCharacteristics.add(charas); // Data
        gattCharacteristicData.add(gattCharacteristicGroupData); // UI

        SimpleExpandableListAdapter gattServiceAdapter = new SimpleExpandableListAdapter(
                this,
                gattServiceData,
                android.R.layout.simple_expandable_list_item_2,
                new String[] {LIST_NAME, LIST_UUID},
                new int[] { android.R.id.text1, android.R.id.text2 },
                gattCharacteristicData,
                android.R.layout.simple_expandable_list_item_2,
                new String[] {LIST_NAME, LIST_UUID},
                new int[] { android.R.id.text1, android.R.id.text2 }
        );
        mGattServicesList.setAdapter(gattServiceAdapter);
    }

    private void displayData(String data) {
        if (data != null) {
            homeFragment.setAvatarAngle(data);
        }
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                updateConnectionState(R.string.connected);
                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                updateConnectionState(R.string.disconnected);
                invalidateOptionsMenu();
                clearUI();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                displayGattServices(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }

    public void getPostureData(){
        if (mGattCharacteristics != null) {
            if(mGattCharacteristics.size()==0) return;
            final BluetoothGattCharacteristic characteristic =
                    mGattCharacteristics.get(0).get(0);
            final int charaProp = characteristic.getProperties();
            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                if (mNotifyCharacteristic != null) {
                    mBluetoothLeService.setCharacteristicNotification(mNotifyCharacteristic, false);
                    mNotifyCharacteristic = null;
                }
                mBluetoothLeService.readCharacteristic(characteristic);
            }
            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                mNotifyCharacteristic = characteristic;
                mBluetoothLeService.setCharacteristicNotification(
                        characteristic, true);
            }
        }
    }

    public final ExpandableListView.OnChildClickListener servicesListClickListner =
            new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                            int childPosition, long id) {
                    if (mGattCharacteristics != null) {
                        final BluetoothGattCharacteristic characteristic =
                                mGattCharacteristics.get(groupPosition).get(childPosition);
                        final int charaProp = characteristic.getProperties();
                        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                            if (mNotifyCharacteristic != null) {
                                mBluetoothLeService.setCharacteristicNotification(
                                        mNotifyCharacteristic, false);
                                mNotifyCharacteristic = null;
                            }
                            mBluetoothLeService.readCharacteristic(characteristic);
                        }
                        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                            mNotifyCharacteristic = characteristic;
                            mBluetoothLeService.setCharacteristicNotification(
                                    characteristic, true);
                        }
                        return true;
                    }
                    return false;
                }
            };

}
