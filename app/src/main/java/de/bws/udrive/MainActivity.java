package de.bws.udrive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

/**
 * Login-Activity (App wird hier gestartet) <br>
 * Öffnet bei erfolgreichem Login oder erfolgreicher Registrierung die {@link HomeActivity}
 */
public class MainActivity extends AppCompatActivity {

    /* UI Elemente */
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPagerAdapter adapter;


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

}