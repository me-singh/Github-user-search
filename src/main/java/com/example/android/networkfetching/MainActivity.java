package com.example.android.networkfetching;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

//    TextView textView;
    Button button;
    RecyclerView recyclerView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        //button craetion on UI using java
//        Button button=new Button(this);
//        button.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT));
//        button.setText("DOWNLOAD");
//
//        LinearLayout linearLayout= (LinearLayout) findViewById(R.id.linearLayout);
//        linearLayout.addView(button);

        editText= (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyAsyncTask myAsyncTask=new MyAsyncTask();
                myAsyncTask.execute("https://api.github.com/search/users?q="+editText.getText().toString());
                //"https://www.google.com"
            }
        });

//        textView = (TextView) findViewById(R.id.textView);
        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
    }

    ArrayList<User> parseJson(String s){

        ArrayList<User> users=new ArrayList<>();

        try {
            JSONObject jsonObject=new JSONObject(s);
            JSONArray items=jsonObject.getJSONArray("items");

            for (int i=0;i<items.length();i++)
            {
                JSONObject currentItem=items.getJSONObject(i);

                String id=currentItem.getString("id");
                String avatar=currentItem.getString("avatar_url");
                String score=currentItem.getString("score");
                String login=currentItem.getString("login");
                String url=currentItem.getString("html_url");

                User user=new User(login,id,avatar,url,score);

                users.add(user);

            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return users;
    }

    public class MyAsyncTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {

            URL url=null;//same as String object with some restrictions
            HttpURLConnection httpURLConnection=null;
            String result="";

            try {
                url=new URL(strings[0]);
                httpURLConnection=(HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("GET");

                httpURLConnection.setReadTimeout(3000);

                httpURLConnection.setConnectTimeout(5000);

                httpURLConnection.connect();

                if (httpURLConnection.getResponseCode()!=HttpURLConnection.HTTP_OK){
                    throw  new IOException("HTTP ERROR"+httpURLConnection.getResponseCode());
                }

                InputStream inputStream=httpURLConnection.getInputStream();

                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);

                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

                StringBuilder stringBuilder=new StringBuilder();
                String line;

                while ((line=bufferedReader.readLine())!=null){
                    stringBuilder.append(line);
                }
                result =stringBuilder.toString();

            }
            catch (IOException e){
                e.printStackTrace();
            }

            return result;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            textView.setText(s);

            ArrayList<User> usersList=parseJson(s);//calling method parseJson->converts Json object into arrayList

            recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(),2));

            UsersAdapter usersAdapter=new UsersAdapter(getBaseContext(),usersList);

            recyclerView.setAdapter(usersAdapter);

//            button.setVisibility(View.GONE);//make view gone
        }
    }
}
