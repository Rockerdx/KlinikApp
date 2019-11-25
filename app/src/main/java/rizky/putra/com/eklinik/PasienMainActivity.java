package rizky.putra.com.eklinik;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import rizky.putra.com.eklinik.fragment.DokterFragment;
import rizky.putra.com.eklinik.fragment.KlinikFragment;
import rizky.putra.com.eklinik.fragment.ProfileFragment;

public class PasienMainActivity extends AppCompatActivity {

    KlinikFragment klinikFragment;
    DokterFragment dokterFragment;
    ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasien_main);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Daftar Klinik");


        BottomNavigationViewEx bnve =  findViewById(R.id.bnve);

        klinikFragment = new KlinikFragment();
        dokterFragment = new DokterFragment();
        profileFragment = new ProfileFragment();

        bnve.enableAnimation(false);
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);
        bnve.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewFragment(klinikFragment);


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
            case R.id.klinik:
                viewFragment(klinikFragment);
                getSupportActionBar().setTitle("Daftar Klinik");
                return true;
            case R.id.dokter:
                viewFragment(dokterFragment);
                getSupportActionBar().setTitle("Daftar Dokter");
                return true;
            case R.id.profile:
                viewFragment(profileFragment);
                getSupportActionBar().setTitle("Profil");
                return true;
        }
        return false;
    };

}
