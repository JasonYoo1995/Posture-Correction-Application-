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
//연간 그래프
public class StatisticsYearActivity extends AppCompatActivity {
    //Button btnBarChart,btnPieChart;
    Spinner spinner;
    Switch sw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year);
        LineChart chart = findViewById(R.id.lineChart);
        YAxis leftYAxis = chart.getAxisLeft();
        leftYAxis.setAxisMaxValue(100f);

        sw=(Switch)findViewById(R.id.switch1);
        CheckState();

        sw.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                CheckState();
            }
        });


        //Spinner_선택시 화면 전환
        final String[] item = getResources().getStringArray(R.array.time);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, item);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setSelection(5,false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1){//메인화면
                    finish();
                }
                else if(position==2){//일간
                    Intent I = new Intent(StatisticsYearActivity.this, StatisticsDayActivity.class);
                    startActivity(I);
                    finish();
                }
                else if(position==3){//주간이동
                    Intent I = new Intent(StatisticsYearActivity.this, StatisticsWeekActivity.class);
                    startActivity(I);
                    finish();
                }
                else if(position==4){//월간이동
                    Intent I = new Intent(StatisticsYearActivity.this, StatisticsMonthActivity.class);
                    startActivity(I);
                    finish();
                }
                else if (position == 5) {//연간으로 이동
                    Intent I = new Intent(StatisticsYearActivity.this, StatisticsYearActivity.class);
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
        LineChart chart = findViewById(R.id.lineChart);
        TextView textView = findViewById(R.id.text1);
        TextView btn = findViewById(R.id.btn3);
        btn.setText("COMPARE REPORT");
        double sum = 0,sumF=0;
        YAxis leftYAxis = chart.getAxisLeft();
        leftYAxis.setAxisMaxValue(100f);
        if (sw.isChecked()) {
            ArrayList<Entry> GoodBad = new ArrayList<>();
            ArrayList<Entry> GoodBad_Friend = new ArrayList<>();

            GoodBad.add(new Entry(46f,0));
            GoodBad.add(new Entry(51f,1));
            GoodBad.add(new Entry(73f,2));
            GoodBad.add(new Entry(41f,3));
            GoodBad.add(new Entry(60f,4));
            GoodBad.add(new Entry(69f,5));
            GoodBad.add(new Entry(72f,6));
            GoodBad.add(new Entry(74f,7));
            GoodBad.add(new Entry(70f,8));
            GoodBad.add(new Entry(69f,9));
            GoodBad.add(new Entry(0f,10));
            GoodBad.add(new Entry(0f,11));

            GoodBad_Friend.add(new Entry(51f,0));
            GoodBad_Friend.add(new Entry(56f,1));
            GoodBad_Friend.add(new Entry(78f,2));
            GoodBad_Friend.add(new Entry(46f,3));
            GoodBad_Friend.add(new Entry(65f,4));
            GoodBad_Friend.add(new Entry(74f,5));
            GoodBad_Friend.add(new Entry(72f,6));
            GoodBad_Friend.add(new Entry(70f,7));
            GoodBad_Friend.add(new Entry(75f,8));
            GoodBad_Friend.add(new Entry(60f,9));
            GoodBad_Friend.add(new Entry(30f,10));
            GoodBad_Friend.add(new Entry(30f,11));
            //x축, 시간
            ArrayList day = new ArrayList();

            day.add("Jan");
            day.add("Feb");
            day.add("Mar");
            day.add("Apr");
            day.add("May");
            day.add("Jun");
            day.add("Jul");
            day.add("Aug");
            day.add("Seb");
            day.add("Oct");
            day.add("Nov");
            day.add("Dec");

            //그래프 구현
//            LineDataSet lineDataSet=new LineDataSet(GoodBad,"Good Posture");
//            LineDataSet lineDataSet_Friend=new LineDataSet(GoodBad_Friend,"Good Posture_Friend");
            chart.animateY(100);
//            LineData data = new LineData(day,lineDataSet);
//            chart.setData(data);
//            lineDataSet.setDrawFilled(true);
//            lineDataSet.setColor(ColorTemplate.getHoloBlue());
//            lineDataSet.setDrawValues(true);
//            lineDataSet.setValueTextSize(10);
//            lineDataSet.setValueTextColor(Color.BLACK);
//            lineDataSet.setHighLightColor(Color.RED);
//            lineDataSet.setHighlightLineWidth(1.0f);
//            ArrayList<ILineDataSet> lineDataSets=new ArrayList<>();
//            lineDataSets.add(lineDataSet);
//            lineDataSets.add(lineDataSet_Friend);
//            LineData data = new LineData(day, lineDataSet);
//            LineData data2 = new LineData(day,lineDataSet_Friend);
//
//            LineData Data = new LineData(day,lineDataSets);
//            chart.setData(Data);
//            lineDataSet.setDrawValues(true);
//            lineDataSet.setColor(Color.BLUE);
//            lineDataSet.setValueTextSize(10);
//            lineDataSet.setValueTextColor(Color.BLACK);
//
//            lineDataSet_Friend.setColor(Color.RED);
//            lineDataSet_Friend.setDrawValues(true);
//            lineDataSet_Friend.setValueTextSize(10);
//            lineDataSet_Friend.setValueTextColor(Color.BLACK);
            LineData data = new LineData(day);
            LineDataSet set = new LineDataSet(GoodBad,"User");
            set.setColor(Color.BLUE);
            set.setDrawValues(true);
            set.setValueTextSize(10);
            set.setValueTextColor(Color.BLACK);
            data.addDataSet(set);

            LineDataSet set_Friend = new LineDataSet(GoodBad_Friend,"Friend");
            data.addDataSet(set_Friend);
            set_Friend.setColor(Color.RED);
            set_Friend.setDrawValues(true);
            set_Friend.setValueTextSize(10);
            set_Friend.setValueTextColor(Color.BLACK);
            chart.setData(data);

            YAxis yAxisRight = chart.getAxisRight(); //Y축의 오른쪽면 설정
            yAxisRight.setDrawLabels(false);
            yAxisRight.setDrawAxisLine(false);
            yAxisRight.setDrawGridLines(false);

            chart.invalidate();

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
        }
        else{
            //현재는 임의로 값을 배정, 이후 mysql값을 받아오는 것으로 수정예정
            btn.setText("ANNUAL REPORT");
            ArrayList<Entry> GoodBad = new ArrayList<>();

            GoodBad.add(new Entry(46f,0));
            GoodBad.add(new Entry(51f,1));
            GoodBad.add(new Entry(73f,2));
            GoodBad.add(new Entry(41f,3));
            GoodBad.add(new Entry(60f,4));
            GoodBad.add(new Entry(69f,5));
            GoodBad.add(new Entry(72f,6));
            GoodBad.add(new Entry(74f,7));
            GoodBad.add(new Entry(70f,8));
            GoodBad.add(new Entry(69f,9));
            GoodBad.add(new Entry(0f,10));
            GoodBad.add(new Entry(0f,11));

            //x축, 시간
            ArrayList day = new ArrayList();

            day.add("Jan");
            day.add("Feb");
            day.add("Mar");
            day.add("Apr");
            day.add("May");
            day.add("Jun");
            day.add("Jul");
            day.add("Aug");
            day.add("Seb");
            day.add("Oct");
            day.add("Nov");
            day.add("Dec");
            //그래프 구현
//            LineDataSet lineDataSet=new LineDataSet(GoodBad,"Good Posture");
//            chart.animateY(100);
//            LineData data = new LineData(day,lineDataSet);
//            chart.setData(data);
//            //lineDataSet.setDrawFilled(true);
//            lineDataSet.setColor(ColorTemplate.getHoloBlue());
//            lineDataSet.setDrawValues(true);
//            lineDataSet.setValueTextSize(10);
//            lineDataSet.setValueTextColor(Color.BLACK);
//            lineDataSet.setHighLightColor(Color.RED);
//            lineDataSet.setHighlightLineWidth(1.0f);

            LineData data = new LineData(day);
            LineDataSet set = new LineDataSet(GoodBad,"User");
            set.setColor(Color.BLUE);
            set.setDrawValues(true);
            set.setValueTextSize(10);
            set.setValueTextColor(Color.BLACK);
            data.addDataSet(set);
            chart.setData(data);

            YAxis yAxisRight = chart.getAxisRight(); //Y축의 오른쪽면 설정
            yAxisRight.setDrawLabels(false);
            yAxisRight.setDrawAxisLine(false);
            yAxisRight.setDrawGridLines(false);

            chart.invalidate();
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