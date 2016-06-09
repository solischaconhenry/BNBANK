package cr.ac.itcr.bnbank.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import cr.ac.itcr.bnbank.model.User;

/**
 * Created by usuario on 7/6/2016.
 */
public class MyPreferenceManager {
    private String TAG = MyPreferenceManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "sugef";

    // All Shared Preferences Keys
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_PASSWORD = "user_pass";
    private static final String KEY_USER_TYPE = "user_type";
    // Constructor
    public MyPreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void storeUser(User user) {
        editor.putString(KEY_USER_ID, user.get_id());
        editor.putString(KEY_USER_NAME, user.getUser());
        editor.putString(KEY_USER_EMAIL, user.getEmail());
        editor.putString(KEY_USER_PASSWORD, user.getPassword());
        editor.putString(KEY_USER_TYPE, user.getType());
        editor.commit();

        Log.e(TAG, "User is stored in shared preferences. " + user.getUser() + ", " + user.getEmail());
    }

    public User getUser() {
        if (pref.getString(KEY_USER_ID, null) != null) {
            String id, name, email, password,type;
            id = pref.getString(KEY_USER_ID, null);
            name = pref.getString(KEY_USER_NAME, null);
            email = pref.getString(KEY_USER_EMAIL, null);
            password = pref.getString(KEY_USER_PASSWORD, null);
            type = pref.getString(KEY_USER_TYPE, null);

            User user = new User(name, password, id, email,type);
            return user;
        }
        return null;
    }



    public void clear() {
        editor.clear();
        editor.commit();
    }
}
