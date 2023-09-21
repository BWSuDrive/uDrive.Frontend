package de.bws.udrive;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.OnBackPressedCallback;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import de.bws.udrive.databinding.ActivityHomeBinding;
import de.bws.udrive.utilities.APIInterface;
import de.bws.udrive.utilities.APIClient;
import de.bws.udrive.utilities.Tag;
import de.bws.udrive.utilities.model.*;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Home-Activity (Hauptbildschirm)
 * Wird bei erfolgreichem Login von {@link MainActivity} geöffnet
 */
public class HomeActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarHome.toolbar);


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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        ((TextView) findViewById(R.id.userField)).setText(uDrive.General.getUserName());
        ((TextView) findViewById(R.id.mailField)).setText(uDrive.General.getUserMail());

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
    private View.OnClickListener bindingListener = view -> Snackbar.make(view, "To be implemented...", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
}