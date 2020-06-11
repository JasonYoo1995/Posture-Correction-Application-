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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

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

        makeBottomNavigation();
        makeDialog();

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
                    case R.id.account:
                        Toast.makeText(MainActivity.this, "My Account",Toast.LENGTH_SHORT).show();break;
                    case R.id.settings:
                        Toast.makeText(MainActivity.this, "Settings",Toast.LENGTH_SHORT).show();break;
                    case R.id.mycart:
                        Toast.makeText(MainActivity.this, "My Cart",Toast.LENGTH_SHORT).show();break;
                    default:
                        return true;
                }
                return true;
            }
        });
    }

    private void makeBottomNavigation(){
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_contents, R.id.navigation_home, R.id.navigation_statistics)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
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
        if (id == R.id.profile_menu) {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.device_information_menu) {
            Intent intent = new Intent(MainActivity.this, DeviceInformationActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.description_menu) {
            Intent intent = new Intent(MainActivity.this, DescriptionActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.customer_service_menu) {
            Intent intent = new Intent(MainActivity.this, CustomerServiceActivity.class);
            startActivity(intent);
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

    private void makeToast(String string){
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
    }
}