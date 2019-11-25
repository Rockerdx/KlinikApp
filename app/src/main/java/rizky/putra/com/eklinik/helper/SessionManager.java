package rizky.putra.com.eklinik.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import rizky.putra.com.eklinik.model.Dokter;
import rizky.putra.com.eklinik.model.Pasien;


public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "RockerLogin";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_NAME = "nameUser";
    private static final String KEY_EMAIL = "hpUser";
    private static final String KEY_STATUS = "statusUser";
    private static final String KEY_UMUR = "umurUser";
    private static final String KEY_ID = "idUser";
    private static final String KEY_FOTO = "idFOTO";
    private static final String KEY_TYPE = "idTYPE";


    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn,int type, Object data ) {
        Pasien pasien;
        Dokter dokter;
        if(type==1){
            pasien = (Pasien) data;
            Log.d(TAG, "pasien logged in!");

            editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
            editor.putString(KEY_ID, String.valueOf(pasien.getId()));
            editor.putString(KEY_NAME, pasien.getNama());
            editor.putString(KEY_EMAIL, pasien.getEmail());
            editor.putString(KEY_STATUS,pasien.getKelamin());
            editor.putInt(KEY_UMUR,pasien.getUmur());
            editor.putString(KEY_TYPE,"pasien");
            editor.commit();
        }
        else if(type==2){
            dokter = (Dokter) data;

            Log.d(TAG, "dokter logged in!");

            editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
            editor.putString(KEY_ID, dokter.getId());
            editor.putString(KEY_NAME, dokter.getNama());
            editor.putString(KEY_EMAIL, dokter.getEmail());
            editor.putString(KEY_STATUS,dokter.getHp());
            editor.putInt(KEY_UMUR,dokter.getSpesialis());
            editor.putString(KEY_FOTO,dokter.getFoto());
            editor.putString(KEY_TYPE,"dokter");
            editor.commit();
        }


        Log.d(TAG, "User login session modified!");
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public String getTypeUser(){
        return pref.getString(KEY_TYPE,"pasien");
    }
    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }


    public Object getData(int type){
        if(type==1){
            Pasien pasien = new Pasien();
            pasien.setId(Integer.valueOf(pref.getString(KEY_ID,"1")));
            pasien.setNama(pref.getString(KEY_NAME,"Unknown"));
            pasien.setEmail(pref.getString(KEY_EMAIL,""));
            pasien.setKelamin(pref.getString(KEY_STATUS,""));
            pasien.setUmur(pref.getInt(KEY_UMUR,1));
            return pasien;
        }
        else {
            Dokter dokter = new Dokter();
            dokter.setId(pref.getString(KEY_ID,"1"));
            dokter.setNama(pref.getString(KEY_NAME,"Unknown"));
            dokter.setEmail(pref.getString(KEY_EMAIL,""));
            dokter.setHp(pref.getString(KEY_STATUS,""));
            dokter.setSpesialis(pref.getInt(KEY_UMUR,1));
            dokter.setFoto(pref.getString(KEY_FOTO,"https://randomuser.me/api/portraits/men/13.jpg"));
            return dokter;
        }

    }
}