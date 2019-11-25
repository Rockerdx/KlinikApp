package stt.harapan.com.eklinik;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stt.harapan.com.eklinik.connection.ApiClient;
import stt.harapan.com.eklinik.model.Dokter;
import stt.harapan.com.eklinik.model.Pasien;
import stt.harapan.com.eklinik.model.Result;

public class PasienRegisterActivity extends AppCompatActivity {

    private ActionProcessButton btnRegister;
    private TextView btnLinkToLogin;
    private TextInputEditText inputFullName;
    private TextInputEditText inputEmail;
    private TextInputEditText inputAge;
    private TextInputEditText inputPassword;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasien_register);

        inputFullName = findViewById(R.id.nama);
        inputEmail = findViewById(R.id.email);
        inputAge = findViewById(R.id.age);
        inputPassword = findViewById(R.id.password);
        spinner =findViewById(R.id.sp_gender);
        btnRegister = findViewById(R.id.btnRegister);
        btnLinkToLogin = findViewById(R.id.btnLinkToLoginScreen);

        btnRegister.setMode(ActionProcessButton.Mode.ENDLESS);

        btnRegister.setOnClickListener(view -> {
            String name = inputFullName.getText().toString().trim();
            String email = inputEmail.getText().toString().trim();
            String age = inputAge.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            if (!name.isEmpty() && !email.isEmpty() && !age.isEmpty() && !password.isEmpty() ) {
                if (spinner.getSelectedItemPosition() != 0) {
                    inputFullName.setEnabled(false);
                    inputEmail.setEnabled(false);
                    inputAge.setEnabled(false);
                    inputPassword.setEnabled(false);
                    btnRegister.setEnabled(false);
                    btnRegister.setProgress(1);

                    Pasien pasien = new Pasien();
                    pasien.setNama(inputFullName.getText().toString());
                    pasien.setEmail(inputEmail.getText().toString());
                    pasien.setKelamin(String.valueOf(spinner.getSelectedItemPosition()));
                    pasien.setUmur(Integer.valueOf(inputAge.getText().toString()));
                    registerProcessWithRetrofit(pasien, password);
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        "Please enter your details!", Toast.LENGTH_LONG)
                        .show();
            }
        });


        btnLinkToLogin.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(),
                    PasienLoginActivity.class);
            startActivity(i);
            finish();
        });
    }

    private void registerProcessWithRetrofit(Pasien pasien, String password){
        ApiClient.getKlinikInterface().registerPasien(pasien.getNama(),
                pasien.getEmail(),
                pasien.getKelamin(),
                String.valueOf(pasien.getUmur()),
                password,
                "pasien"
                ).enqueue(new Callback<Result<String>>() {
            @Override
            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                Result<String> userResponse = response.body();
                if(userResponse!=null) {
                    if (!userResponse.getError()) {

                        Toast.makeText(PasienRegisterActivity.this, "Register Berhasil", Toast.LENGTH_SHORT).show();
                        Intent x = new Intent(PasienRegisterActivity.this, PasienLoginActivity.class);
                        x.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(x);
                    } else {
                        Log.d("tes", new Gson().toJson(response.body()));
                        Toast.makeText(PasienRegisterActivity.this, "Register Gagal", Toast.LENGTH_SHORT).show();
                        inputFullName.setEnabled(true);
                        inputEmail.setEnabled(true);
                        inputAge.setEnabled(true);
                        inputPassword.setEnabled(true);
                        btnRegister.setEnabled(true);
                        btnRegister.setProgress(0);
                    }
                }
            }

            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {
                inputFullName.setEnabled(true);
                inputEmail.setEnabled(true);
                inputAge.setEnabled(true);
                inputPassword.setEnabled(true);
                btnRegister.setEnabled(true);
                btnRegister.setProgress(0);

            }
        });
    }
}
