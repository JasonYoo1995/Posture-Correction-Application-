package com.example.bottomtab.statistics;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
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

import com.example.bottomtab.R;
import com.example.bottomtab.etc.Data;
import com.example.bottomtab.etc.MainActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class StatisticsFragment extends Fragment {
    MainActivity mainActivity;
    View rootView;
    public String user_id;

    public StatisticsFragment(MainActivity mainActivity) {
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
//        LineChart chart = rootView.findViewById(R.id.lineChart);
//        YAxis leftYAxis = chart.getAxisLeft();
//        leftYAxis.setAxisMaxValue(100f);
        // todo
        //예시: month는 +1을 더해준다
        //이후 날짜 데이터를 받아오면 쓰일 예정
        //현재 날짜 출력
//        TextView textView= (TextView)rootView.findViewById(R.id.statistics_textView);
//        TextView text1 = (TextView) rootView.findViewById(R.id.text1);
//        double sum=0;
        String format =  new String("Current Date: yyyy-MM-dd");

        // [서버 통신]
        String hour = "20";
        JSONTaskGET task = new JSONTaskGET();
        //String parameter = "?id="+user_id+"&year="+year+"&month="+"&day="+"&hour="; // 특정 year의 기록을 month 단위로 받아옴
        //String parameter = "?id="+user_id+"&year="+year+"&month="+month+"&day="+"&hour="; // 특정 month의 기록을 day 단위로 받아옴
        String parameter = "?id="+user_id+"&year="+year+"&month="+month+"&day="+day+"&hour="; // 특정 day의 기록을 hour 단위로 받아옴
        //String parameter = "?id="+user_id+"&year="+year+"&month="+month+"&day="+day+"&hour="+hour; // 특정 hour의 기록을 minute 단위로 받아옴
        task.execute("http://3.92.215.113:4001/posture"+parameter);
        /*
        <DB에 있는 기록 기간>
        2018년 5월 31일 2시 20분 ~ 29분
        2020년 9월 26일 19시 40분 ~ 21시 19분
        */



//        textView.setText("Current Date: "+year+"-"+(month+1)+"-"+day);
//        if(year==2020&&month==8&&day==26) {
//                    Intent intent = new Intent(MainActivity.this, DayActivity.class);
//                    startActivity(intent);
        //현재는 임의로 값을 배정, 이후 mysql값을 받아오는 것으로 수정예정

//            ArrayList<Entry> GoodBad = new ArrayList<>();
//
//            GoodBad.add(new BarEntry(0f,0));
//            GoodBad.add(new BarEntry(0f,1));
//            GoodBad.add(new BarEntry(0f,2));
//            GoodBad.add(new BarEntry(0f,3));
//            GoodBad.add(new BarEntry(0f,4));
//            GoodBad.add(new BarEntry(0f,5));
//            GoodBad.add(new BarEntry(46f,6));
//            GoodBad.add(new BarEntry(46f,7));
//            GoodBad.add(new BarEntry(51f,8));
//            GoodBad.add(new BarEntry(73f,9));
//            GoodBad.add(new BarEntry(41f,10));
//            GoodBad.add(new BarEntry(60f,11));
//            GoodBad.add(new BarEntry(69f,12));
//            GoodBad.add(new BarEntry(69f,13));
//            GoodBad.add(new BarEntry(69f,14));
//            GoodBad.add(new BarEntry(0f,15));
//            GoodBad.add(new BarEntry(0f,16));
//            GoodBad.add(new BarEntry(0f,17));
//            GoodBad.add(new BarEntry(45f,18));
//            GoodBad.add(new BarEntry(40f,19));
//            GoodBad.add(new BarEntry(50f,20));
//            GoodBad.add(new BarEntry(0f,21));
//            GoodBad.add(new BarEntry(0f,22));
//            GoodBad.add(new BarEntry(0f,23));
//
//            //x축, 시간
//            ArrayList<String> date = new ArrayList<>();
//            for(int i=0;i<24;i++){
//                date.add(i+"am");
//            }

        //여기에서 GoodBad와 date를 불러옴
        //그래프 구현




        //Daily Report 사용자
        //사용자의 기록이 0인 경우를 제외하고 평균을 구한다.

        //기준값과 사용자의 데이터 비교

//        }
//        else{
//
//            //chart.clear();
//            chart.setNoDataText("There's no record of this day.");
//            text1.setText("There's no evaluate of this day.");
//
//        }
    }

    class JSONTaskGET extends AsyncTask<String, String, String> {
        ArrayList<Data> data = new ArrayList<>();

        public void setData(ArrayList<Data> data) {
            this.data = data;
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                for (int i = 0; i < data.size(); i++) {
                    jsonObject.accumulate(data.get(i).key, data.get(i).value);
                }
                HttpURLConnection con = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL(urls[0]);
                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();
                    //서버로 부터 데이터를 받음
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                    StringBuffer buffer = new StringBuffer();
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (con != null) {
                        con.disconnect();
                    }
                    try {
                        if (reader != null) {
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //key의 데이터값을 담는다
            LineChart chart = rootView.findViewById(R.id.lineChart);
            YAxis leftYAxis = chart.getAxisLeft();
            leftYAxis.setAxisMaxValue(100f);
            TextView textView = (TextView) rootView.findViewById(R.id.statistics_textView);
            TextView text1 = (TextView) rootView.findViewById(R.id.text1);
            double sum = 0;

            System.out.println("RESULT = "+ result);

            if (result == null || result.equals("")) return;
            try {
                JSONArray jsonResult = new JSONArray(result);
                if (jsonResult == null) return;

                String temp = new JSONObject(jsonResult.getString(0)).toString().substring(2, 4);
                String key = null;
                if (temp.equals("Mo")) key = "Month";
                else if (temp.equals("Da")) key = "Day";
                else if (temp.equals("Ho")) key = "Hour";
                else if (temp.equals("Mi")) key = "Min";

                ArrayList<Entry> GoodBad = new ArrayList<>();
                for (int i=0; i<24; i++){
                    GoodBad.add(new Entry(0, i));
                }

                for (int i = 0; i < jsonResult.length(); i++) {
                    int hour = 0;
                    int degree = 0;

                    JSONObject entity = new JSONObject(jsonResult.getString(i));
                    hour = Integer.parseInt(entity.getString(key));

                    Double d1 = Double.parseDouble(entity.getString("AVG(LR)"));
                    Double d2 = Double.parseDouble(entity.getString("AVG(FB)"));
                    int d = (int) (d1 + d2) / 2;
                    degree = d;

                    GoodBad.set(hour, new Entry(degree, hour));
                }

                //년월일 미완성
//                textView.setText("Current Date: " + year + "-" + (month + 1) + "-" + day);
//                if (year == 2020 && month == 8 && day == 26) {

                ArrayList<String> date = new ArrayList<>();
                for (int i = 0; i < 24; i++) {
                    date.add(i + "am");
                }

                LineDataSet lineDataSet = new LineDataSet(GoodBad, "Good Posture");
                chart.animateY(100);
                LineData data = new LineData(date, lineDataSet);
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

                int count = 0;
                for (int i = 0; i < GoodBad.size(); i++) {
                    sum += GoodBad.get(i).getVal();
                    if (GoodBad.get(i).getVal() != 0f) {
                        count = count + 1;
                    }
                }
                sum = sum / count;

                if (sum < 30f)
                    text1.setText("Ooops! Your Posture is quite bad!!");
                else if (sum <= 65f)
                    text1.setText("Well done!! You are like a tree!");
                else
                    text1.setText("Master of Posture! Your will is like steel!!");

//                }
//                else {
//
//                    //chart.clear();
//                    chart.setNoDataText("There's no record of this day.");
//                    text1.setText("There's no evaluate of this day.");
//
//                }

            } catch (JSONException e) {
                e.printStackTrace();
//            if(string.equals("")) string = "결과 없음";
//            ((TextView)rootView.findViewById(R.id.temp_text)).setText(string);
            }
        }

//        private void makeToast(String string) {
//            Toast.makeText(getContext(), string, Toast.LENGTH_SHORT).show();
//        }
    }
}