package site.aanrstudio.apps.katalogfilm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Switch;

import site.aanrstudio.apps.katalogfilm.notification.AlarmNotification;

public class SettingActivity extends AppCompatActivity {

    private Preferences preferences = new Preferences();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.setting_activity));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        RadioButton lang_ind = findViewById(R.id.lang_ind);
        RadioButton lang_en = findViewById(R.id.lang_en);
        Switch switch_daily = findViewById(R.id.switch_daily);
        Switch switch_release = findViewById(R.id.switch_release);

        final AlarmNotification alarmNotification = new AlarmNotification();

        String lang = preferences.getLang(this);
        boolean notif_user = preferences.getNotifUser(this);
        boolean notif_new = preferences.getNotifNew(this);

        switch_daily.setChecked(notif_user);
        switch_release.setChecked(notif_new);

        switch_daily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences.setNotifUser(buttonView.getContext(), isChecked);
                if (isChecked) {
                    alarmNotification.setAlarmUser(buttonView.getContext(), "07:00");
                } else {
                    alarmNotification.cancelAlarmUser(buttonView.getContext());
                }
            }
        });

        switch_release.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences.setNotifNew(buttonView.getContext(), isChecked);
                if (isChecked) {
                    alarmNotification.setAlarmNew(buttonView.getContext(), "08:00");
                } else {
                    alarmNotification.cancelAlarmNew(buttonView.getContext());
                }
            }
        });

        if (lang.equals("en")) {
            lang_en.setChecked(true);
        } else if (lang.equals("in")) {
            lang_ind.setChecked(true);
        }

        lang_ind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.setLang(v.getContext(),"in");
            }
        });
        lang_en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.setLang(v.getContext(), "en");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
