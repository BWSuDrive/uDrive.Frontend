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

import de.bws.udrive.databinding.ActivityHomeBinding;
import de.bws.udrive.utilities.APIInterface;
import de.bws.udrive.utilities.APIClient;
import de.bws.udrive.utilities.Tag;
import de.bws.udrive.utilities.model.*;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    private TextView userField;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarHome.toolbar);

        this.userName = getIntent().getStringExtra("username");

        Log.i(Tag.INFO, "Current User: " + userName);

        testAPIConnection();

        this.handleListeners();

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_nachrichten, R.id.nav_stundenplan)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    /*
    Test-Methode um Verbindung mit API zu testen, wird bei Bedarf entfernt
    */
    private void testAPIConnection()
    {
        APIInterface i = APIClient.getAPI().create(APIInterface.class);
        uDrive.Login login = new uDrive.Login("someId", "someName",
                "someDescription", 5,
                "2023-09-13T16:20:00.000Z");
        Call<ResponseBody> loginTest = i.testAPIEndpoint(login);
        Call<ResponseBody> loginTestGET = i.testAPIEndpoint();

        loginTest.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(Tag.SUCCESS, "HTTP Method: " + call.request().method());
                Log.d(Tag.SUCCESS, "Response-Code: " + response.code());
                Log.d(Tag.SUCCESS, response.message());
                Log.d(Tag.SUCCESS, "Successful: " + response.isSuccessful());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(Tag.FAILURE, "Message: " + t.getMessage());
                Log.e(Tag.FAILURE, "Cause: " + t.getCause());
            }
        });

        loginTestGET.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(Tag.SUCCESS, "HTTP Method: " + call.request().method());
                Log.d(Tag.SUCCESS, "Response-Code: " + response.code());
                Log.d(Tag.SUCCESS, response.message());
                Log.d(Tag.SUCCESS, "Successful: " + response.isSuccessful());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(Tag.FAILURE, "Message: " + t.getMessage());
                Log.e(Tag.FAILURE, "Cause: " + t.getCause());
            }
        });
    }

    /**
     * Setzt die Listener für einzelne Objekte
     * */
    private void handleListeners()
    {
        binding.appBarHome.fab.setOnClickListener(bindingListener);

        getOnBackPressedDispatcher().addCallback(onBackPressed);
    }

    /* Seitenmenu, das per Klick auf den Hamburger geöffnet wird */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        userField = findViewById(R.id.userField);
        userField.setText(this.userName);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /* Verhindert, dass der User mit dem Zurück-Knopf auf die MainActivity (den Login-Screen) kommt */
    private OnBackPressedCallback onBackPressed = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            HomeActivity.this.moveTaskToBack(true);
        }
    };

    /* OnClickListener für Button in der HomeActivity */
    private View.OnClickListener bindingListener = view -> Snackbar.make(view, "To be implemented...", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
}