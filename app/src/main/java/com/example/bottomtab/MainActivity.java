package com.example.bottomtab;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView drawerNavigationView;
    Dialog optionDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        makeDrawerNavigation();
        makeDialog();
        addBottomTabEvents();
    }

    private void addBottomTabEvents() {
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        findViewById(R.id.contents_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationView.setSelectedItemId(R.id.contents_menu);
                setTitle(R.string.title_contents);
                makeToast("정보 컨텐츠 클릭 시 이벤트 발생");
            }
        });
        findViewById(R.id.home_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationView.setSelectedItemId(R.id.home_menu);
                setTitle(R.string.title_home);
                makeToast("자세 측정 클릭 시 이벤트 발생");
            }
        });
        findViewById(R.id.statistics_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationView.setSelectedItemId(R.id.statistics_menu);
                setTitle(R.string.title_statistics);
                makeToast("통계 분석/예측 클릭 시 이벤트 발생");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        if (id == R.id.bluetooth_menu) {
            makeToast("블루투스");
            return true;
        }
        if (id == R.id.option_menu) {
            optionDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void makeDialog(){
        optionDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar);
        optionDialog.setContentView(R.layout.option_layout);
        optionDialog.setCancelable(true);
        optionDialog.getWindow().setBackgroundDrawableResource(android.R.color.background_light);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Display display = getWindowManager().getDefaultDisplay();
        layoutParams.width = display.getWidth() - 200;
        layoutParams.height = display.getHeight() - 400;
        optionDialog.getWindow().setAttributes(layoutParams);

        optionDialog.findViewById(R.id.button_exit).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                optionDialog.dismiss();
            }
        });
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
                    case R.id.description_menu:
                        startActivity(new Intent(MainActivity.this, DescriptionActivity.class));
                        break;
                    case R.id.customer_service_menu:
                        startActivity(new Intent(MainActivity.this, CustomerServiceActivity.class));
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });
    }

    private void makeToast(String string){
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
    }
}