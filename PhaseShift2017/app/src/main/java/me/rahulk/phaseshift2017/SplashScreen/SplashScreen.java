package me.rahulk.phaseshift2017.SplashScreen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.rahulk.phaseshift2017.Admin.Admin;
import me.rahulk.phaseshift2017.MainActivity;
import me.rahulk.phaseshift2017.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreen extends Activity {

    private TextView txtRemDays, txtRemHours, txtRemMins, txtRemSeconds;
    private View viewCountdown, mainLayout;
    private Handler handler;
    private Runnable runnable;
    private boolean activityLaunched = false;

    private int SPLASH_TIME_OUT = 1500;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        txtRemDays = (TextView) findViewById(R.id.txtRemDays);
        txtRemHours = (TextView) findViewById(R.id.txtRemHours);
        txtRemMins = (TextView) findViewById(R.id.txtRemMins);
        txtRemSeconds = (TextView) findViewById(R.id.txtRemSeconds);

        viewCountdown = findViewById(R.id.viewCountdown);

        mainLayout = findViewById(R.id.mainLayout);

        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMainActivity();
            }
        });

        sharedPreferences = this.getSharedPreferences("PhaseShift2017", this.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (isFirstTimeLaunch()) {
            setFirstTimeLaunch(false);
            SPLASH_TIME_OUT = 3500;
        }

        countDownStart();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                launchMainActivity();
            }
        }, SPLASH_TIME_OUT);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean("IsFirstTimeLaunch_App", isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return sharedPreferences.getBoolean("IsFirstTimeLaunch_App", true);
    }

    public void countDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    // Here Set your Event Date
                    Date eventDate = dateFormat.parse("2017-09-15 10:00:00");
                    Date currentDate = new Date();
                    if (!currentDate.after(eventDate)) {
                        long diff = eventDate.getTime()
                                - currentDate.getTime();
                        long days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        long hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        long minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        long seconds = diff / 1000;
                        txtRemDays.setText("" + String.format("%02d", days));
                        txtRemHours.setText("" + String.format("%02d", hours));
                        txtRemMins.setText("" + String.format("%02d", minutes));
                        txtRemSeconds.setText("" + String.format("%02d", seconds));
                    } else {
                        viewCountdown.setVisibility(View.GONE);
                        handler.removeCallbacks(runnable);
                        // handler.removeMessages(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

    public void launchMainActivity() {
        if (!activityLaunched) {
            activityLaunched = true;
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}
