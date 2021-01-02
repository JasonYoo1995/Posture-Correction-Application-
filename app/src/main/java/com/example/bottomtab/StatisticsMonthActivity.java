package com.example.bottomtab;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

//월간 그래프
//기존: 원형그래프였으나 수정필요
//대체: 꺾은선 그래프로 임시조치 Piechart->Linechart
public class StatisticsMonthActivity extends AppCompatActivity {
    Switch sw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month);
        LineChart lineChart = findViewById(R.id.LineChart);

        sw=(Switch)findViewById(R.id.switch1);
        CheckState();

        sw.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
             CheckState();
            }
        });


        //Spinner 화면 전환
        final String[] item = getResources().getStringArray(R.array.time);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, item);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setSelection(4,false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1){//월간->메인
                    finish();
                }
                else if(position==2){//월간->일간
                    Intent I = new Intent(StatisticsMonthActivity.this, StatisticsDayActivity.class);
                    startActivity(I);
                    finish();
                }
                else if(position==3){//주간
                    Intent I = new Intent(StatisticsMonthActivity.this, StatisticsWeekActivity.class);
                    startActivity(I);
                    finish();
                }
                else if(position==4){//월간
                    Intent I = new Intent(StatisticsMonthActivity.this, StatisticsMonthActivity.class);
                    startActivity(I);
                    finish();
                }
                else if (position == 5) {//연간으로 이동
                    Intent I = new Intent(StatisticsMonthActivity.this, StatisticsYearActivity.class);
                    startActivity(I);
                    finish();
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void CheckState() {
        LineChart lineChart = findViewById(R.id.LineChart);
        double sum = 0,sumF=0;
        TextView textView = findViewById(R.id.text1);

        //mysql값을 받아오는 것으로 변경예정
        //친구와의 비교
        if (sw.isChecked()) {
            TextView btn = findViewById(R.id.btn3);
            btn.setText("COMPARE REPORT");
            ArrayList <Entry>GoodBad = new ArrayList();
            ArrayList<Entry> GoodBad_Friend = new ArrayList<>();

            GoodBad.add(new Entry(46f, 0));
            GoodBad.add(new Entry(51f, 1));
            GoodBad.add(new Entry(73f, 2));
            GoodBad.add(new Entry(41f, 3));
            GoodBad.add(new Entry(60f, 4));
//            GoodBad.add(new Entry(69f, 5));
//            GoodBad.add(new Entry(69f, 6));
//            GoodBad.add(new Entry(69f, 7));
//            GoodBad.add(new Entry(73f, 8));
//            GoodBad.add(new Entry(41f, 9));

            GoodBad_Friend.add(new Entry(69f,0));
            GoodBad_Friend.add(new Entry(69f,1));
            GoodBad_Friend.add(new Entry(69f,2));
            GoodBad_Friend.add(new Entry(73f,3));
            GoodBad_Friend.add(new Entry(41f,4));

            LineDataSet dataSet = new LineDataSet(GoodBad, "Good Posture");
            ArrayList day = new ArrayList();
            //x축 기간
            day.add("4week");
            day.add("3week");
            day.add("2week");
            day.add("1week");
            day.add("Recent");
//            day.add("4week.f");
//            day.add("3week.f");
//            day.add("2week.f");
//            day.add("1week.f");
//            day.add("Recent.f");

//            LineData data = new LineData(day, dataSet);
            LineData data = new LineData(day);
            LineDataSet set = new LineDataSet(GoodBad,"User");
            set.setColor(Color.BLUE);
            set.setDrawValues(true);
            set.setValueTextSize(10);
            set.setValueTextColor(Color.BLACK);
            data.addDataSet(set);

//            lineChart.animateXY(100, 100);
//            //색 설정
//            dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
            LineDataSet set_Friend = new LineDataSet(GoodBad_Friend,"Friend");
            data.addDataSet(set_Friend);
            set_Friend.setColor(Color.RED);
            set_Friend.setDrawValues(true);
            set_Friend.setValueTextSize(10);
            set_Friend.setValueTextColor(Color.BLACK);
            lineChart.setData(data);

            YAxis yAxisRight = lineChart.getAxisRight(); //Y축의 오른쪽면 설정
            yAxisRight.setDrawLabels(false);
            yAxisRight.setDrawAxisLine(false);
            yAxisRight.setDrawGridLines(false);

            lineChart.setDescription("Compare Graph");
            lineChart.invalidate();
            //Daily Report 구현_친구와 비교
            for(int i=0;i<GoodBad.size();i++){
                sum +=GoodBad.get(i).getVal();
            }
            sum=sum/GoodBad.size();
            for(int i=0;i<GoodBad.size();i++){
                sumF +=GoodBad_Friend.get(i).getVal();
            }
            sumF=sumF/GoodBad_Friend.size();

            //평균값을 친구의 데이터와 비교한다.
            if(sum<sumF)
                textView.setText("Ooooops! Your posture is worse than your friend's. You look like a Luigi!");
            else if(sum==sumF)
                textView.setText("You are neck and neck with your friend!");
            else
                textView.setText("You are better than your friend! Be Proud of that!");

        } else {
            //개인측정
            TextView btn = findViewById(R.id.btn3);
            btn.setText("MONTHLY REPORT");
            //mysql값을 받아오는 것으로 변경예정
            ArrayList<Entry> GoodBad = new ArrayList<>();


            GoodBad.add(new Entry(46f, 0));
            GoodBad.add(new Entry(51f, 1));
            GoodBad.add(new Entry(73f, 2));
            GoodBad.add(new Entry(41f, 3));
            GoodBad.add(new Entry(60f, 4));


            LineDataSet dataSet = new LineDataSet(GoodBad, "Good Posture");
            ArrayList day = new ArrayList();
            //x축 기간

            day.add("4week");
            day.add("3week");
            day.add("2week");
            day.add("1week");
            day.add("Recent");


//            LineData data = new LineData(day, dataSet);
            LineData data = new LineData(day);
            LineDataSet set = new LineDataSet(GoodBad,"User");
            set.setColor(Color.BLUE);
            set.setDrawValues(true);
            set.setValueTextSize(10);
            set.setValueTextColor(Color.BLACK);
            data.addDataSet(set);
            lineChart.setData(data);
            YAxis yAxisRight = lineChart.getAxisRight(); //Y축의 오른쪽면 설정
            yAxisRight.setDrawLabels(false);
            yAxisRight.setDrawAxisLine(false);
            yAxisRight.setDrawGridLines(false);

            lineChart.setDescription("Compare Graph");
            lineChart.invalidate();
            //Daily Report 사용자
            for(int i=0;i<GoodBad.size();i++){
                sum +=GoodBad.get(i).getVal();
            }
            sum=sum/GoodBad.size();

            //기준값과 사용자의 데이터 비교
            if(sum<30f)
                textView.setText("Ooops! Your Posture is quite bad!!");
            else if(sum<=65f)
                textView.setText("Well done!! You are like a tree!");
            else
                textView.setText("Master of Posture! Your will is like steel!!");

        }
    }
}