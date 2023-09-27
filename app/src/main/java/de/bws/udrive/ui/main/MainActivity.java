package de.bws.udrive.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import de.bws.udrive.R;
import de.bws.udrive.ui.home.HomeActivity;

/**
 * Login-Activity (App wird hier gestartet) <br>
 * Öffnet bei erfolgreichem Login oder erfolgreicher Registrierung die {@link HomeActivity}
 */
public class MainActivity extends AppCompatActivity {

    private final String TAG = "uDrive." + getClass().getSimpleName();

    /* UI Elemente */
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPagerAdapter adapter;

    private final int REQUEST_CODE = 1;


    /**
     * Konstruktor-Methode für MainActivity <br>
     * Zeigt den Login & Registrierungs Screen an <br>
     *
     * @author Fabian
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Initialisierung von Objekten */
        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);

        /* Neue Tabs hinzufügen */
        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Signup"));

        /* Adapter setzen */
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new ViewPagerAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(adapter);

        /* Listener & Callbacks setzen */
        tabLayout.addOnTabSelectedListener(tabSelectedListener);
        viewPager2.registerOnPageChangeCallback(onPageChange);

        /* Nach Standort-Berechtigung fragen */
        askLocationPermissions();
    }

    /* =================================================================================== */

    /**
     * OnTabSelectedListener für tabLayout Objekt
     *
     * @author Fabian
     */
    private final TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener()
    {
        @Override
        public void onTabSelected(TabLayout.Tab tab) { viewPager2.setCurrentItem(tab.getPosition()); }
        @Override
        public void onTabUnselected(TabLayout.Tab tab) { }
        @Override
        public void onTabReselected(TabLayout.Tab tab) { }
    };

    /**
     * OnPageChangeCallback für viewPager2 Objekt
     *
     * @author Fabian
     */
    private final ViewPager2.OnPageChangeCallback onPageChange = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) { tabLayout.selectTab(tabLayout.getTabAt(position)); }
    };

    private void askLocationPermissions()
    {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if(requestCode == REQUEST_CODE)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Log.i(TAG, "Berechtigungen für Standort genehmigt!");
            }
            else
            {
                Toast.makeText(this, "Bitte aktiviere die Standort-Berechtigung!", Toast.LENGTH_LONG).show();
                this.finishAffinity();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}