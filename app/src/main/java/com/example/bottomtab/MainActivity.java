package com.example.bottomtab;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

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

    // bluetooth ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    BluetoothAdapter mBluetoothAdapter;
    Set<BluetoothDevice> mPairedDevices;

    Handler mBluetoothHandler;
    ConnectedBluetoothThread mThreadConnectedBluetooth;
    BluetoothDevice mBluetoothDevice;
    BluetoothSocket mBluetoothSocket;

    final static int BT_REQUEST_ENABLE = 1;
    final static int BT_MESSAGE_READ = 2;
    final static int BT_CONNECTING_STATUS = 3;
    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    // bluetooth ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    String user_id;

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

        // bluetooth ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothHandler = new Handler(){
            public void handleMessage(android.os.Message msg){
                if(msg.what == BT_MESSAGE_READ){
                    String readMessage = null;
                    try {
                        readMessage = new String((byte[]) msg.obj, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    int x = 0, y = 0;
                    int middleIndex = 0;
                    for(int i=0; i<8; i++){
                        try {
                            if (readMessage.charAt(i) == '@') {
                                x = Integer.parseInt(readMessage.substring(0, i));
                                middleIndex = i + 1;
                                continue;
                            }
                            if (readMessage.charAt(i) == '\n') {
                                y = Integer.parseInt(readMessage.substring(middleIndex, i - 1));
                                break;
                            }
                        }
                        catch(NumberFormatException e){
                            System.out.println("Number Format exception");
                        }
                    }
                    homeFragment.setPostureData("x축 : "+x+"\ny축 : "+y);
                    homeFragment.setAvatarAngle(y, x);
                    System.out.println(x +"/"+y);
                }
            }
        };
        // bluetooth ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        // LoginActivity로부터 로그인 시 유저 아이디를 받아옴
        Bundle extras = getIntent().getExtras();
        user_id = extras.getString("user_id");
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

    // bluetooth ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    void bluetoothOn() {
        if(mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "블루투스를 지원하지 않는 기기입니다.", Toast.LENGTH_SHORT).show();
        }
        else {
            if (mBluetoothAdapter.isEnabled()) {
                Toast.makeText(getApplicationContext(), "블루투스가 이미 활성화 되어 있습니다.", Toast.LENGTH_SHORT).show();
//                mTvBluetoothStatus.setText("활성화");
            }
            else {
//                Toast.makeText(getApplicationContext(), "블루투스가 활성화 되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
                Intent intentBluetoothEnable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intentBluetoothEnable, BT_REQUEST_ENABLE);
            }
        }
    }
    void bluetoothOff() {
        if (mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();
            Toast.makeText(getApplicationContext(), "블루투스가 비활성화 되었습니다.", Toast.LENGTH_SHORT).show();
//            mTvBluetoothStatus.setText("비활성화");

            homeFragment.setState0();
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
//                    mTvBluetoothStatus.setText("활성화");
                } else if (resultCode == RESULT_CANCELED) { // 블루투스 활성화를 취소를 클릭하였다면
//                    Toast.makeText(getApplicationContext(), "취소", Toast.LENGTH_SHORT).show();
//                    mTvBluetoothStatus.setText("비활성화");

                    homeFragment.setState0();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    void listPairedDevices() {
        if (mBluetoothAdapter.isEnabled()) { // 블루투스가 켜져있으면
            mPairedDevices = mBluetoothAdapter.getBondedDevices(); // 페어링 가능한 모든 블루투스 장치들을 리턴
            if (mPairedDevices.size() > 0) { // 페어링 가능한 장치가 하나라도 있으면
                // 페어링 가능한 모든 장치의 목록을 띄움
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("장치 선택");

                ArrayList<String> mListPairedDevices = new ArrayList<String>();
                for (BluetoothDevice device : mPairedDevices) {
                    mListPairedDevices.add(device.getName());
                    //mListPairedDevices.add(device.getName() + "\n" + device.getAddress());
                }
                final CharSequence[] items = mListPairedDevices.toArray(new CharSequence[mListPairedDevices.size()]);
                mListPairedDevices.toArray(new CharSequence[mListPairedDevices.size()]);

                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        connectSelectedDevice(items[item].toString());
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

//                connectSelectedDevice("HC-06");
            }
            else { // 페어링 가능한 장치가 하나도 없으면
                Toast.makeText(getApplicationContext(), "페어링된 장치가 없습니다.", Toast.LENGTH_SHORT).show();
            }
        }
        else { // 블루투스가 꺼져있으면
            Toast.makeText(getApplicationContext(), "블루투스가 비활성화 되어 있습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        if(Build.VERSION.SDK_INT >= 10){
            try {
                final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord",
                        new Class[] { UUID.class });
                return (BluetoothSocket) m.invoke(device, BT_UUID);
            }
            catch (Exception e) {
                Log.e("TAG", "Could not create Insecure RFComm Connection",e); } }
        return device.createRfcommSocketToServiceRecord(BT_UUID);
    }

    void connectSelectedDevice(String selectedDeviceName) {
//        Log.d("ABCD","waiting");
        for(BluetoothDevice tempDevice : mPairedDevices) {
            if (selectedDeviceName.equals(tempDevice.getName())) {
//                Log.d("ABCD","same");
                mBluetoothDevice = tempDevice;
                break;
            }
        }
        try {
            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(BT_UUID);
            mBluetoothSocket = createBluetoothSocket(mBluetoothDevice);
            mBluetoothSocket.connect();
            makeToast("기기와 연결되었습니다.");
            mThreadConnectedBluetooth = new ConnectedBluetoothThread(mBluetoothSocket);
            mThreadConnectedBluetooth.start();
            mBluetoothHandler.obtainMessage(BT_CONNECTING_STATUS, 1, -1).sendToTarget();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "기기와 연결을 실패했습니다.", Toast.LENGTH_SHORT).show();
            homeFragment.setState0();
        }
    }

    public class ConnectedBluetoothThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedBluetoothThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "소켓 연결 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;
            System.out.println("----------------------");
            while (true) {
                try {
                    bytes = mmInStream.available();
                    if (bytes != 0) {
                        SystemClock.sleep(100);
                        bytes = mmInStream.available();
                        bytes = mmInStream.read(buffer, 0, bytes);
                        mBluetoothHandler.obtainMessage(BT_MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                    }
                } catch (IOException e) {
                    break;
                }
            }
        }
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
//                Toast.makeText(getApplicationContext(), "소켓 해제 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }

    // bluetooth ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

}