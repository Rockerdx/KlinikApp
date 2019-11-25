package rizky.putra.com.eklinik;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rizky.putra.com.eklinik.connection.ApiClient;
import rizky.putra.com.eklinik.helper.SessionManager;
import rizky.putra.com.eklinik.model.Pasien;
import rizky.putra.com.eklinik.model.Result;


public class PasienLoginActivity extends AppCompatActivity {


    SessionManager session;
    ActionProcessButton btnLogin;
    TextInputEditText inputPhone;
    TextInputEditText inputPassword;
    TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        inputPhone = findViewById(R.id.hp);
        register = findViewById(R.id.register);
        inputPassword = findViewById(R.id.password);

        btnLogin.setMode(ActionProcessButton.Mode.ENDLESS);

        session = new SessionManager(getApplicationContext());

//        if (session.isLoggedIn()) {
//            Intent intent = new Intent(PasienLoginActivity.this, DokterMainActivity.class);
//            startActivity(intent);
//            finish();
//        }

        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputPhone.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty()) {
                    inputPhone.setEnabled(false);
                    inputPassword.setEnabled(false);
                    btnLogin.setEnabled(false);
                    loginProcessWithRetrofit(email,password);
                    btnLogin.setProgress(1);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        PasienRegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    private void loginProcessWithRetrofit(final String phone, String password){

        ApiClient.getKlinikInterface().loginPasienWithEmailPass(phone,password,"user").enqueue(new Callback<Result<Pasien>>() {
            @Override
            public void onResponse(Call<Result<Pasien>> call, Response<Result<Pasien>> response) {
                Result<Pasien> userResponse = response.body();
                if(!userResponse.getError()) {
                    Pasien user = (Pasien) userResponse.getData();
                    session.setLogin(true,1,user);
                    Intent x = new Intent(PasienLoginActivity.this, PasienMainActivity.class);
                    x.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(x);
                    finish();
                }
                else {
                    btnLogin.setProgress(0);
                    inputPhone.setEnabled(true);
                    btnLogin.setEnabled(true);
                    inputPassword.setText("");
                    inputPassword.setEnabled(true);
                    Toast.makeText(PasienLoginActivity.this,"Failed to login" ,Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Result<Pasien>> call, Throwable t) {
                btnLogin.setProgress(0);
                inputPhone.setEnabled(true);
                inputPassword.setText("");
                btnLogin.setEnabled(true);
                inputPassword.setEnabled(true);
                btnLogin.setEnabled(true);
                Toast.makeText(PasienLoginActivity.this,"Failed to login",Toast.LENGTH_SHORT).show();

            }
        });
    }



}
