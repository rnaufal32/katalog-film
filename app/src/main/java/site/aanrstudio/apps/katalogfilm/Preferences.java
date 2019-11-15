package site.aanrstudio.apps.katalogfilm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class Preferences {

    private static String PREF_LANG = "pref_lang";
    private static final String PREF_NOTIF_USER = "pref_notif_user";
    private static final String PREF_NOTIF_NEW = "pref_notif_new";
    private Locale locale;

    private static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setLang(Context context, String lang) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(PREF_LANG, lang);
        editor.apply();
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public String getLang(Context context) {
        locale = context.getResources().getConfiguration().locale;
        return getSharedPreference(context).getString(PREF_LANG, "in");
    }

    public void setLocale(Context context, BottomNavigationView bottomNavigationView) {
        locale = new Locale(getLang(context));
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
        bottomNavigationView.getMenu().clear();
        bottomNavigationView.inflateMenu(R.menu.navigation);
    }

    public void setFavorite(Context context, int id, boolean is_fav) {        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(String.valueOf(id), is_fav).apply();
    }

    public boolean getFavorite(Context context, int id) {
        return getSharedPreference(context).getBoolean(String.valueOf(id), false);
    }

    public boolean getNotifUser(Context context) {
        return getSharedPreference(context).getBoolean(PREF_NOTIF_USER, true);
    }

    public void setNotifUser(Context context, boolean action) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(PREF_NOTIF_USER, action).apply();
    }

    public boolean getNotifNew(Context context) {
        return getSharedPreference(context).getBoolean(PREF_NOTIF_NEW, true);
    }

    public void setNotifNew(Context context, boolean action) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(PREF_NOTIF_NEW, action).apply();
    }
}
