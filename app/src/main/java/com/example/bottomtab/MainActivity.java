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

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    Dialog optionDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeBottomNavigation();
        makeDialog();
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