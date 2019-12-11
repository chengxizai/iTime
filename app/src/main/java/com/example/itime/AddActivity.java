package com.example.itime;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.itime.ui.home.Cat;
import com.example.itime.ui.home.CatAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    private FloatingActionButton fab_return,fab_ok;
    private EditText edt;
    private ListView lv1;
    private int editPosition;
    private List<Cat> cats = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().hide();

        fab_return = findViewById(R.id.fab_1);
        fab_ok = findViewById(R.id.fab_2);
        edt = findViewById(R.id.editText_name);
        lv1=findViewById(R.id.list_add);

        editPosition = getIntent().getIntExtra("edit_position", 0);
        String goodName = getIntent().getStringExtra("name");
        if (goodName != null) {
            edt.setText(goodName);
        }

        //返回
        fab_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddActivity.this.finish();
            }
        });
        //确定
        fab_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edt.getText().length()==0){
                    Toast.makeText(AddActivity.this,"标题不能为空！请重试",Toast.LENGTH_SHORT).show();
                }else if(DataHolder.getInstance().getYear()==0){
                    Toast.makeText(AddActivity.this,"请选择日期！",Toast.LENGTH_SHORT).show();;
                }
                else{
                    Intent intent = new Intent();
                    intent.putExtra("edit_position", editPosition);
                    intent.putExtra("name", edt.getText().toString().trim());
                    setResult(1, intent);
                    DataHolder.getInstance().setName(edt.getText().toString());
                    AddActivity.this.finish();
                }
            }
        });

        //设置日期
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Calendar ca = Calendar.getInstance();
                    int  mYear = ca.get(Calendar.YEAR);
                    int  mMonth = ca.get(Calendar.MONTH);
                    int  mDay = ca.get(Calendar.DAY_OF_MONTH);
                    int hour = ca.get(Calendar.HOUR_OF_DAY);
                    int minute = ca.get(Calendar.MINUTE);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(AddActivity.this,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    int myhours=hourOfDay;
                                    int myminute=minute;
                                    final String time = myhours + ":" + myminute;
                                    DataHolder.getInstance().setHour(myhours);
                                    DataHolder.getInstance().setMinute(myminute);
                                }
                            }, hour, minute,true);
                    timePickerDialog.show();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    int mYear = year;
                                    int mMonth = month;
                                    int mDay = dayOfMonth;
                                    final String date = year + "年" + (month+1) + "月" + dayOfMonth + "日";
                                    DataHolder.getInstance().setYear(mYear);
                                    DataHolder.getInstance().setMonth(mMonth);
                                    DataHolder.getInstance().setDay(mDay);
                                    DataHolder.getInstance().setTime(date);
                                }
                            },
                            mYear, mMonth, mDay);
                    datePickerDialog.show();

                }
            }
        });

        init();
        CatAdapter adapter = new CatAdapter(this, R.layout.cat_item, cats);
        ((ListView) findViewById(R.id.list_add)).setAdapter(adapter);
    }

    private void init() {
        cats.add(new Cat("日期", R.drawable.ic_access_time_black_24dp));
        cats.add(new Cat("重复设置", R.drawable.ic_replay_black_24dp));
        cats.add(new Cat("图片", R.drawable.ic_photo_size_select_actual_black_24dp));
        cats.add(new Cat("添加标签", R.drawable.ic_loyalty_black_24dp));
    }
}
