package com.example.itime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TimeActivity extends AppCompatActivity {

    private FloatingActionButton fab_back,fab_delete,fab_chang;
    private TextView mDays_Tv, mHours_Tv, mMinutes_Tv, mSeconds_Tv,time,name;
    private Timer mTimer;

    Calendar ca = Calendar.getInstance();
    int cYear = ca.get(Calendar.YEAR);
    int cMonth = ca.get(Calendar.MONTH);
    int cDay = ca.get(Calendar.DAY_OF_MONTH);
    int chour = ca.get(Calendar.HOUR_OF_DAY);
    int cminute = ca.get(Calendar.MINUTE);
    int cscond = ca.get(Calendar.SECOND);

    int year_int = DataHolder.getInstance().getYear();
    int month_int = DataHolder.getInstance().getMonth();
    int day_int = DataHolder.getInstance().getDay();
    int hour_int = DataHolder.getInstance().getHour();
    int minute_int = DataHolder.getInstance().getMinute();
    String dateEnd=month_int+"/"+day_int+"/"+year_int;
    String dateBegin=cMonth+"/"+cDay+"/"+cYear;
    Date dateOfEnd=new Date(dateEnd);
    Date dateOfBegin=new Date(dateBegin);

    long diff=dateOfEnd.getTime() - dateOfBegin.getTime();
    private long mDay = diff/(1000*3600*24);
    private long mHour = 24-chour;
    private long mMin =60-cminute;
    private long mSecond = 60-cscond;

    private Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                computeTime();
                mDays_Tv.setText(mDay+"");//天数不用补位
                mHours_Tv.setText(getTv(mHour));
                mMinutes_Tv.setText(getTv(mMin));
                mSeconds_Tv.setText(getTv(mSecond));
                if (mSecond == 0 &&  mDay == 0 && mHour == 0 && mMin == 0 ) {
                    mTimer.cancel();
                }
            }
        }
    };

    private String getTv(long l){
        if(l>=10){
            return l+"";
        }else{
            return "0"+l;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        getSupportActionBar().hide();

        fab_back=findViewById(R.id.fab_back);
        fab_delete=findViewById(R.id.fab_delete);
        fab_chang=findViewById(R.id.fab_create);
        time=findViewById(R.id.time_time);
        name=findViewById(R.id.name);

        String date = DataHolder.getInstance().getTime();
        time.setText(date);
        String title = DataHolder.getInstance().getName();
        name.setText(title);

        mTimer = new Timer();
        mDays_Tv = (TextView) findViewById(R.id.days_tv);
        mHours_Tv = (TextView) findViewById(R.id.hours_tv);
        mMinutes_Tv = (TextView) findViewById(R.id.minutes_tv);
        mSeconds_Tv = (TextView) findViewById(R.id.seconds_tv);
        startRun();


        fab_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = getIntent().getStringExtra("delete_position");
                Intent intent = new Intent();
                intent.putExtra("position",title);
                setResult(2, intent);
                TimeActivity.this.finish();
            }
        });
        fab_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeActivity.this.finish();
            }
        });
        fab_chang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TimeActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });
    }

    private void startRun() {
        TimerTask mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.what = 1;
                timeHandler.sendMessage(message);
            }
        };
        mTimer.schedule(mTimerTask,0,1000);
    }

     //倒计时计算
    private void computeTime() {
        mSecond--;
        if (mSecond < 0) {
            mMin--;
            mSecond = 59;
            if (mMin < 0) {
                mMin = 59;
                mHour--;
                if (mHour < 0) {
                    mHour = 23;
                    mDay--;
                    if(mDay < 0){
                        mDay = 0;
                        mHour= 0;
                        mMin = 0;
                        mSecond = 0;
                    }
                }
            }
        }
    }
}
