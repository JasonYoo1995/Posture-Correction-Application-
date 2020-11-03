package com.example.bottomtab;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class StatisticsFragment extends Fragment {
    MainActivity mainActivity;
    View rootView;
    int current_position = 1;

    StatisticsFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_statistics, container, false);
        //현재 날짜를 출력
        TextView textView= (TextView) rootView.findViewById(R.id.statistics_textView);
        String format =  new String("현재 날짜: "+"yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.KOREA);
        textView.setText(sdf.format(new Date()));

        //Spinner_선택시 화면 전환
        final String[] item = getResources().getStringArray(R.array.time);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, item);
        final Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setSelection(0, false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {//Main화면에서 Main화면으로 =>변동없음
                } else if (position == 2) {//일간으로 이동
                    Intent I = new Intent(getActivity(), StatisticsDayActivity.class);
                    startActivity(I);
                } else if (position == 3) {//주간으로 이동
                    Intent I = new Intent(getActivity(), StatisticsWeekActivity.class);
                    startActivity(I);
                } else if (position == 4) {//월간으로 이동
                    Intent I = new Intent(getActivity(), StatisticsMonthActivity.class);
                    startActivity(I);
                } else if (position == 5) {//연간으로 이동
                    Intent I = new Intent(getActivity(), StatisticsYearActivity.class);
                    startActivity(I);
                }
                spinner.setSelection(1, false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rootView.findViewById(R.id.btn_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(null);
            }
        });
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showDatePicker(View view){
        DialogFragment newFragment = new DatePickerFragment(this);
        newFragment.show(mainActivity.getSupportFragmentManager(),"datePicker");
    }

    public void processDatePickerResult(int year,int month,int day){
        LineChart chart = rootView.findViewById(R.id.lineChart);
        YAxis leftYAxis = chart.getAxisLeft();
        leftYAxis.setAxisMaxValue(100f);
        // todo
        //예시: month는 +1을 더해준다
        //이후 날짜 데이터를 받아오면 쓰일 예정
        //현재 날짜 출력
        TextView textView= (TextView)rootView.findViewById(R.id.statistics_textView);
        TextView text1 = (TextView) rootView.findViewById(R.id.text1);
        double sum=0;
        String format =  new String("Current Date: yyyy-MM-dd");
        textView.setText("Current Date: "+year+"-"+(month+1)+"-"+day);
        if(year==2020&&month==9&&day==24) {
//                    Intent intent = new Intent(MainActivity.this, DayActivity.class);
//                    startActivity(intent);
            //현재는 임의로 값을 배정, 이후 mysql값을 받아오는 것으로 수정예정

            ArrayList<Entry> GoodBad = new ArrayList<>();

            GoodBad.add(new BarEntry(0f,0));
            GoodBad.add(new BarEntry(0f,1));
            GoodBad.add(new BarEntry(0f,2));
            GoodBad.add(new BarEntry(0f,3));
            GoodBad.add(new BarEntry(0f,4));
            GoodBad.add(new BarEntry(0f,5));
            GoodBad.add(new BarEntry(46f,6));
            GoodBad.add(new BarEntry(46f,7));
            GoodBad.add(new BarEntry(51f,8));
            GoodBad.add(new BarEntry(73f,9));
            GoodBad.add(new BarEntry(41f,10));
            GoodBad.add(new BarEntry(60f,11));
            GoodBad.add(new BarEntry(69f,12));
            GoodBad.add(new BarEntry(69f,13));
            GoodBad.add(new BarEntry(69f,14));
            GoodBad.add(new BarEntry(0f,15));
            GoodBad.add(new BarEntry(0f,16));
            GoodBad.add(new BarEntry(0f,17));
            GoodBad.add(new BarEntry(45f,18));
            GoodBad.add(new BarEntry(40f,19));
            GoodBad.add(new BarEntry(50f,20));
            GoodBad.add(new BarEntry(0f,21));
            GoodBad.add(new BarEntry(0f,22));
            GoodBad.add(new BarEntry(0f,23));

            //x축, 시간
            ArrayList<String> date = new ArrayList<>();

            date.add("0am");
            date.add("1am");
            date.add("2am");
            date.add("3am");
            date.add("4am");
            date.add("5am");
            date.add("6am");
            date.add("7am");
            date.add("8am");
            date.add("9am");
            date.add("10am");
            date.add("11am");
            date.add("12pm");
            date.add("13pm");
            date.add("14pm");
            date.add("15pm");
            date.add("16pm");
            date.add("17pm");
            date.add("18pm");
            date.add("19pm");
            date.add("20pm");
            date.add("21pm");
            date.add("22pm");
            date.add("23pm");

            //그래프 구현
            LineDataSet lineDataSet=new LineDataSet(GoodBad,"Good Posture");
            chart.animateY(100);
            LineData data = new LineData(date,lineDataSet);
            chart.setData(data);
//            lineDataSet.setDrawFilled(true);
            lineDataSet.setColor(ColorTemplate.getHoloBlue());
            lineDataSet.setDrawValues(true);

            YAxis yAxisRight = chart.getAxisRight(); //Y축의 오른쪽면 설정
            yAxisRight.setDrawLabels(false);
            yAxisRight.setDrawAxisLine(false);
            yAxisRight.setDrawGridLines(false);

            lineDataSet.setValueTextSize(6);
            lineDataSet.setValueTextColor(Color.BLACK);
            lineDataSet.setHighLightColor(Color.RED);
            lineDataSet.setHighlightLineWidth(1.0f);

            //Daily Report 사용자
            //사용자의 기록이 0인 경우를 제외하고 평균을 구한다.
            int count =0;
            for(int i=0;i<GoodBad.size();i++){
                sum +=GoodBad.get(i).getVal();
                if (GoodBad.get(i).getVal()!=0f) {
                    count=count+1;
                }
            }
            sum=sum/count;

            //기준값과 사용자의 데이터 비교
            if(sum<30f)
                text1.setText("Ooops! Your Posture is quite bad!!");
            else if(sum<=65f)
                text1.setText("Well done!! You are like a tree!");
            else
                text1.setText("Master of Posture! Your will is like steel!!");
        }
        else{

            chart.clear();
            chart.setNoDataText("There's no record of this day.");
            text1.setText("There's no evaluate of this day.");

        }
    }
}
