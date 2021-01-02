package com.example.bottomtab;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FriendActivity extends AppCompatActivity {
    LinearLayout list_layout;
    EditText editText;
    LayoutInflater layout_inflater;
    LinearLayout.LayoutParams params;
    String user_id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_friend);

        list_layout = findViewById(R.id.friend_list);
        editText = findViewById(R.id.friend_edit_text);

        layout_inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); // layout inflater 생성
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 0, 0, 40);

        // MainActivity로부터 유저 아이디를 받아옴
        Bundle extras = getIntent().getExtras();
        user_id = extras.getString("user_id");

        Button btn = (Button)findViewById(R.id.search_friend);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONTask task = new JSONTask();
                Data[] data = { new Data("user_id", user_id), new Data("friend_id", editText.getText().toString()) };
                task.setData(data);
                task.execute("http://3.92.215.113:4001/add_friend");
            }
        });

        // 친구 목록 load
        JSONTask2 task = new JSONTask2();
        task.setData(new Data("user_id", user_id));
        task.execute("http://3.92.215.113:4001/get_friend");
    }

    private void setList(ArrayList<String> list){
        for(int i=0; i<list.size(); i++){
            addFriend(list.get(i));
        }
    }

    private void addFriend(String name){ // 새 친구를 list_layout에 추가
        RelativeLayout newFriendList = (RelativeLayout) layout_inflater.inflate(R.layout.layout_list, null);
        newFriendList.setLayoutParams(params);
        ((TextView)newFriendList.getChildAt(0)).setText(name);
        ((CheckBox)newFriendList.getChildAt(1)).setOnCheckedChangeListener(new CheckBoxListener());
        list_layout.addView(newFriendList); // 맨 마지막에 리스트 추가
    }

    private class CheckBoxListener implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton view, boolean checked) { // 체크박스를 누를 때 일어날 일
            checked = ((CheckBox) view).isChecked();
            TextView text = (TextView)((RelativeLayout)view.getParent()).getChildAt(0);
            int index = ((LinearLayout)view.getParent().getParent()).indexOfChild((RelativeLayout)view.getParent());
            if(checked==true){ // 체크 ON
                makeToast(index+"번째 리스트 ON");
            }
            else{ //체크 OFF
                makeToast(index+"번째 리스트 OFF");
            }
        }
    }

    class JSONTask extends AsyncTask<String, String, String> {
        Data[] data;
        public void setData(Data[] data){
            this.data = data;
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate(data[0].key, data[0].value);
                jsonObject.accumulate(data[1].key, data[1].value);
                HttpURLConnection con = null;
                BufferedReader reader = null;
                try{
                    //URL url = new URL("http://3.92.215.113:4001/users");
                    URL url = new URL(urls[0]);
                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type", "application/json");//application JSON 식으로 전송
                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();
                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌

                    //서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuffer buffer = new StringBuffer();
                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }
                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임
                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
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
            if(result.equals("-1")){
                makeToast("회원이 존재하지 않습니다.");
            }
            else{
//                makeToast("친구가 추가되었습니다.");
                String string = "";
                if(result==null || result.equals("")) return;
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    string = jsonObject.getString("id");
                    //makeToast(string);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                addFriend(string);
            }
        }
    }

    class JSONTask2 extends AsyncTask<String, String, String> {
        Data data;
        public void setData(Data data){
            this.data = data;
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate(data.key, data.value);
                HttpURLConnection con = null;
                BufferedReader reader = null;
                try{
                    //URL url = new URL("http://3.92.215.113:4001/users");
                    URL url = new URL(urls[0]);
                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type", "application/json");//application JSON 식으로 전송
                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();
                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌

                    //서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuffer buffer = new StringBuffer();
                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }
                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임
                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
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
            String string = "";
            if(result==null || result.equals("")) return;
            try {
                ArrayList<String> friend_list = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(result);
                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                    string = jsonObject.getString("to");
                    friend_list.add(string);
                }
                setList(friend_list);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void makeToast(String string){ // 토스트
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }
}
