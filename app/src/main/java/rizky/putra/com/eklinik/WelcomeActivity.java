package rizky.putra.com.eklinik;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import rizky.putra.com.eklinik.helper.SessionManager;

public class WelcomeActivity extends AppCompatActivity {


    Button login1,login2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        login1 = findViewById(R.id.btnLoginP);
        login2 = findViewById(R.id.btnLoginD);

        SessionManager sessionManager = new SessionManager(this);

        if(sessionManager.isLoggedIn()){
            if(sessionManager.getTypeUser().equals("dokter")){
                startActivity(new Intent(WelcomeActivity.this,DokterMainActivity.class));
                finish();
            }
            else if(sessionManager.getTypeUser().equals("pasien")){
                startActivity(new Intent(WelcomeActivity.this,PasienMainActivity.class));
                finish();
            }
        }

        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this,PasienLoginActivity.class));
                finish();
            }
        });
        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this,DokterLoginActivity.class));
                finish();
            }
        });

    }
}
