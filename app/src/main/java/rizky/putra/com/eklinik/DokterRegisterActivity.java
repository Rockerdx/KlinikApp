package rizky.putra.com.eklinik;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rizky.putra.com.eklinik.connection.ApiClient;
import rizky.putra.com.eklinik.model.Dokter;
import rizky.putra.com.eklinik.model.Result;

public class DokterRegisterActivity  extends AppCompatActivity {

    private ActionProcessButton btnRegister;
    private TextView btnLinkToLogin;
    private TextInputEditText inputFullName;
    private TextInputEditText inputNIP;
    private TextInputEditText inputPhone;
    private TextInputEditText inputEmail;
    private TextInputEditText inputPassword;
    private Spinner spesialis;
    LinearLayout view;
    String link = "";

    private static int GALLERY = 123;
    private static int CAMERA = 321;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokter_register);

        inputFullName = findViewById(R.id.nama);
        inputNIP = findViewById(R.id.nip);
        inputPhone = findViewById(R.id.hp);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        btnRegister = findViewById(R.id.btnRegister);
        spesialis = findViewById(R.id.sp_spesialis);
        btnLinkToLogin = findViewById(R.id.btnLinkToLoginScreen);
        view = findViewById(R.id.view);

        btnRegister.setMode(ActionProcessButton.Mode.ENDLESS);

        btnRegister.setOnClickListener(view -> {
            String name = inputFullName.getText().toString().trim();
            String nip = inputNIP.getText().toString().trim();
            String hp = inputPhone.getText().toString().trim();
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            if (!name.isEmpty() && !nip.isEmpty() && !hp.isEmpty() && !email.isEmpty() && !password.isEmpty() ) {

                if (spesialis.getSelectedItemPosition() != 0) {
                    inputFullName.setEnabled(false);
                    inputNIP.setEnabled(false);
                    inputPhone.setEnabled(false);
                    inputEmail.setEnabled(false);
                    inputPassword.setEnabled(false);
                    btnRegister.setEnabled(false);
                    btnRegister.setProgress(1);

                    Dokter dokter = new Dokter();
                    dokter.setNama(name);
                    dokter.setId(nip);
                    dokter.setHp(hp);
                    dokter.setEmail(email);
                    dokter.setFoto("https://cdn.onlinewebfonts.com/svg/img_491471.png");
                    dokter.setSpesialis(spesialis.getSelectedItemPosition());

                    registerProcessWithRetrofit(dokter, password);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });


        btnLinkToLogin.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(),
                    DokterLoginActivity.class);
            startActivity(i);
            finish();
        });

    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(DokterRegisterActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    private void registerProcessWithRetrofit(Dokter dokter, String password){
        ApiClient.getKlinikInterface().registerDokter(dokter.getNama(),
                dokter.getId(),
                dokter.getHp(),
                dokter.getEmail(),
                String.valueOf(dokter.getSpesialis()),
                dokter.getFoto(),
                password,
                "dokter"
        ).enqueue(new Callback<Result<String>>() {
            @Override
            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                Result<String> userResponse = response.body();
                if(userResponse!=null) {
                    if (!userResponse.getError()) {

                        Toast.makeText(DokterRegisterActivity.this, "Register Berhasil", Toast.LENGTH_SHORT).show();
                        Intent x = new Intent(DokterRegisterActivity.this, DokterLoginActivity.class);
                        x.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(x);
                    } else {
                        Log.d("tes", new Gson().toJson(response.body()));
                        Toast.makeText(DokterRegisterActivity.this, "Register Gagal", Toast.LENGTH_SHORT).show();
                        inputFullName.setEnabled(true);
                        inputNIP.setEnabled(true);
                        inputPhone.setEnabled(true);
                        inputEmail.setEnabled(true);
                        inputPassword.setEnabled(true);
                        btnRegister.setEnabled(true);
                        btnRegister.setProgress(0);
                    }
                }
            }

            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {
                inputFullName.setEnabled(true);
                inputNIP.setEnabled(true);
                inputPhone.setEnabled(true);
                inputEmail.setEnabled(true);
                inputPassword.setEnabled(true);
                btnRegister.setEnabled(true);
                btnRegister.setProgress(0);
            }
        });
    }
}
