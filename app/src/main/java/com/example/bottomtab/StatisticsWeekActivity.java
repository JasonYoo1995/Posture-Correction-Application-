package com.example.bottomtab;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
//주간_막대그래프
public class StatisticsWeekActivity extends AppCompatActivity {

    BarChart stackedChart;
    int[] colorArray=new int[]{Color.BLUE,Color.RED};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);
        BarChart chart = findViewById(R.id.barChart);

        //스택그래프
        ArrayList GoodBad = new ArrayList<>();

        //이후 Mysql값을 받아오는 것으로 변경예정
        GoodBad.add(new BarEntry(new float[]{49f,51f}, 0));
        GoodBad.add(new BarEntry(new float[]{51f, 49f}, 1));
        GoodBad.add(new BarEntry(new float[]{71f, 29f}, 2));
        GoodBad.add(new BarEntry(new float[]{41f, 59f}, 3));
        GoodBad.add(new BarEntry(new float[]{60f, 40f}, 4));
        GoodBad.add(new BarEntry(new float[]{40f, 60f}, 5));
        GoodBad.add(new BarEntry(new float[]{28f,72f},6));

        ArrayList day = new ArrayList();
        //x축, 시간
        day.add("Mon");
        day.add("Tue");
        day.add("Wed");
        day.add("Thu");
        day.add("Fry");
        day.add("Sat");
        day.add("Sun");

        stackedChart=findViewById(R.id.barChart);
        //label: 설명
        BarDataSet barDataSet = new BarDataSet(GoodBad,"Good or Bad");
        //색 설정
        barDataSet.setColors(colorArray);

        BarData barData=new BarData(day,barDataSet);
        stackedChart.setData(barData);
        barData.setDrawValues(true);
        barData.setValueTextSize(10);

        //Spinner 선택시 화면 전환
        final String[] item = getResources().getStringArray(R.array.time);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, item);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setSelection(3,false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1){//메인화면
                    finish();
                }
                else if(position==2){//일간
                    Intent I = new Intent(StatisticsWeekActivity.this, StatisticsDayActivity.class);
                    startActivity(I);
                    finish();
                }
                else if(position==3){//주간으로 이동
                    Intent I = new Intent(StatisticsWeekActivity.this, StatisticsWeekActivity.class);
                    startActivity(I);
                    finish();
                }
                else if(position==4){//월간으로 이동
                    Intent I = new Intent(StatisticsWeekActivity.this, StatisticsMonthActivity.class);
                    startActivity(I);
                    finish();
                }
                else if (position == 5) {//연간으로 이동
                    Intent I = new Intent(StatisticsWeekActivity.this, StatisticsYearActivity.class);
                    startActivity(I);
                    finish();
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}