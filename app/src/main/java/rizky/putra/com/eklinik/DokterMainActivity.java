package rizky.putra.com.eklinik;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import rizky.putra.com.eklinik.fragment.BerandaDokter;
import rizky.putra.com.eklinik.fragment.ProfileFragment;
import rizky.putra.com.eklinik.fragment.UpcomingDokter;

public class DokterMainActivity extends AppCompatActivity {

    BerandaDokter berandaDokter;
    UpcomingDokter upcomingDokter;
    ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokter_main);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Jadwal Hari ini");


        BottomNavigationViewEx bnve =  findViewById(R.id.bnve);

        berandaDokter = new BerandaDokter();
        upcomingDokter = new UpcomingDokter();
        profileFragment = new ProfileFragment();

        bnve.enableAnimation(false);
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);
        bnve.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewFragment(berandaDokter);


    }

    private void viewFragment(Fragment fragment) {
        final android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
//        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }
    private BottomNavigationViewEx.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.beranda:
                        viewFragment(berandaDokter);
                        getSupportActionBar().setTitle("Jadwal Hari ini");
                        return true;
                    case R.id.upcoming:
                        viewFragment(upcomingDokter);
                        getSupportActionBar().setTitle("Jadwal Minggu ini");
                        return true;
                    case R.id.profile:
                        viewFragment(profileFragment);
                        getSupportActionBar().setTitle("Profil");
                        return true;
                }
                return false;
            };
}
