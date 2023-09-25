package de.bws.udrive;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import de.bws.udrive.databinding.ActivityHomeBinding;
import de.bws.udrive.utilities.model.*;

/**
 * Home-Activity (Hauptbildschirm)
 * Wird bei erfolgreichem Login von {@link MainActivity} geöffnet
 */
public class HomeActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;

    private FusedLocationProviderClient locationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarHome.toolbar);

        /* Position noch nicht gesetzt */
        if (General.getSignedInUser().getLatitude() == 0.0 && General.getSignedInUser().getLongitude() == 0.0)
            getUserLocation();

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_start, R.id.nav_nachrichten, R.id.nav_meinefahrt)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        getOnBackPressedDispatcher().addCallback(onBackPressed);

    }

    /**
     Seitenmenu, das per Klick auf den Hamburger geöffnet wird
     @author Fabian
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!General.getSignedInUser().hasRole("Driver")) {
            findViewById(R.id.nav_fahrtenplaner).setVisibility(View.GONE);
            findViewById(R.id.nav_meinefahrt).setVisibility(View.GONE);
        }

        getMenuInflater().inflate(R.menu.home, menu);

        ((TextView) findViewById(R.id.userField)).setText(General.getSignedInUser().getVorname() + " " + General.getSignedInUser().getNachname());
        ((TextView) findViewById(R.id.mailField)).setText(General.getSignedInUser().getMail());

        this.invalidateOptionsMenu();

        return true;
    }

    /**
     tbd
     @author Fabian
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     Verhindert, dass der User mit dem Zurück-Knopf auf die MainActivity (den Login-Screen) kommt
     @author Lucas
     */
    private OnBackPressedCallback onBackPressed = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            HomeActivity.this.moveTaskToBack(true);
        }
    };

    /**
     OnClickListener für Button in der HomeActivity
     @author Fabian, Lucas
     */
    private final View.OnClickListener bindingListener = view -> Snackbar.make(view, "To be implemented...", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();

    /* Nach Berechtigung wird in MainActivity gefragt */
    @SuppressLint("MissingPermission")
    private void getUserLocation() {
        this.locationClient = LocationServices.getFusedLocationProviderClient(this);

        this.locationClient.getLastLocation().addOnSuccessListener(successListener);
    }

    private final OnSuccessListener<Location> successListener = location ->
    {
        Log.i("uDrive.HomeActivity", "Latitude: " + location.getLatitude());
        Log.i("uDrive.HomeActivity", "Longitude: " + location.getLongitude());

        General.getSignedInUser().setCoordinates(location.getLatitude(), location.getLongitude());
    };

}