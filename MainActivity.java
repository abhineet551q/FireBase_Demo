package com.aarawholesale.myquiz.firebase_demo;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.security.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;

import java.util.Locale;



public class MainActivity extends AppCompatActivity {


    Switch mSwitch1;
    Switch mSwitch2;
    Context mContext = this;

    TabHost mTabHost;

    Integer l=0,l1=0,l2=0,l3=0,l4=0,l5=0;

     GraphView mGraphView1,mGraphView2,mGraphView3;
     EditText mEditText1,mEditText2,mEditText3;
      DataPoint mDataPoint,mDataPoint1,mDataPoint2;
    LineGraphSeries<DataPoint> series;


    final    HashMap<Integer,Integer> values = new HashMap<>();

    final LineGraphSeries<DataPoint> series4=new LineGraphSeries<>(new DataPoint[]{new DataPoint(0,25)});
    private LineGraphSeries<DataPoint> series5=new LineGraphSeries<>(new DataPoint[]{new DataPoint(0,25)});

    private LineGraphSeries<DataPoint> series6=new LineGraphSeries<>(new DataPoint[]{new DataPoint(0,25)});

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwitch1 = findViewById(R.id.s1);
     //   mSwitch2 = findViewById(R.id.s2);
          mGraphView1 = findViewById(R.id.graph);

        mGraphView2 = findViewById(R.id.graph1);

        mGraphView3 = findViewById(R.id.graph2);
        mEditText1 = findViewById(R.id.edt1);
        mEditText2=findViewById(R.id.edt2);
        mEditText3=findViewById(R.id.edt3);
     //   series = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0,0)});



        mTabHost = findViewById(R.id.tabhost);
        mTabHost.setup();

        TabHost.TabSpec spec = mTabHost.newTabSpec("Lights");
        spec.setContent(R.id.Light1);
        spec.setIndicator("Lights");
        mTabHost.addTab(spec);


        TabHost.TabSpec spec2 = mTabHost.newTabSpec("Motion");
        spec2.setContent(R.id.Light2);
        spec2.setIndicator("Motion");
        mTabHost.addTab(spec2);
        final String[] s1 = {"0"};
       // import android.os.Handler;
        mSwitch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( mSwitch1.isChecked()){
                    s1[0] = "1";

                }
                else{
                    s1[0] = "0";
                }

                try {
                    sendJson(s1[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });

// Create the Handler
      final   Handler handler1 = new Handler();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                Log.d("ghr", "run: ");
                try {
                    fetchData(new VolleyCallBack() {
                        @Override
                        public void onSuccess() {

                            if(!series4.isEmpty()) {
                                series4.setColor(Color.GREEN);
                               // mGraphView1.getViewport().setScalable(true);

// activate horizontal scrolling
                              //  mGraphView1.getViewport().setScrollable(true);

// activate horizontal and vertical zooming and scrolling
                              //  mGraphView1.getViewport().setScalableY(true);

// activate vertical scrolling
                            //    mGraphView1.getViewport().setScrollableY(true);
                                Log.d("ert", "onSuccess: "+series4.isEmpty());


                                mGraphView1.removeAllSeries();
                                mGraphView2.removeAllSeries();
                                mGraphView3.removeAllSeries();

                                mGraphView1.addSeries(series4);

                                mGraphView2.addSeries(series5);

                                mGraphView3.addSeries(series6);



                              // series4.clearReference(mGraphView1);
                              //  series4.resetData(new DataPoint[]{new DataPoint(0, 0)});
                            }
if (l4==0){
                                mEditText1.setText("no motion");
}
else {
    mEditText1.setText("motion detected");
}

                            if (l5==0){
                                mEditText2.setText("no touch");
                            }
                            else {
                                mEditText2.setText("touch  detected");
                            }
                            if (l3==0){
                                mEditText3.setText("no ir");
                            }
                            else {
                                mEditText3.setText("ir detected");
                            }





                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.postDelayed(this,60000);

            }

        }, 0);



// Define the code block to be executed


       // mGraphView2.removeAllSeries();
      //  series3 = (LineGraphSeries<DataPoint>) fetchData();
      //  HashMap<Integer,Integer> value = null;
//        Log.d("Tag00", "onCreate: "+value.size());









    }

public void fetchData(final VolleyCallBack callBack) throws InterruptedException {

    String lightApi = "https://api.thingspeak.com/channels/528079/feeds.json?api_key=Q22N89LWU0XT0JDI&results=100" +
            "";
   final Object n = null;

    JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, lightApi, null,

            new com.android.volley.Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray feeds = response.getJSONArray("feeds");

String f,f1,f2,f3,f4,f5,f6;
boolean r=false,r1=false,r2=false;
int c=0,c1=0,c2=0;
                        for (int i = 0; i < feeds.length(); i++) {
                            JSONObject jo = feeds.getJSONObject(i);

                            if(jo.get("field1")!=JSONObject.NULL) {
                                 f = (String) jo.get("field1");
                                 r=true;
                                 l=Integer.parseInt(f);
                                 c++;
                            }
                            else{
                                r=false;
                            }
                            if(jo.get("field2")!=JSONObject.NULL) {

                                f1 = (String) jo.get("field2");
                                l1=Integer.parseInt(f1);
                                r1=true;

                                c1++;
                            }
                            else{
                                r1=false;
                            }
                            if(jo.get("field3")!=JSONObject.NULL) {

                                f2 = (String) jo.get("field3");
                                l2=Integer.parseInt(f2);
                                r2=true;

                                c2++;
                            }
                            else{
                                r2=false;
                            }

                            if(i==feeds.length()-1) {
                                if (jo.get("field4") != JSONObject.NULL) {

                                    f3 = (String) jo.get("field4");
                                    l3 = Integer.parseInt(f3);


                                }
                                if (jo.get("field5") != JSONObject.NULL) {

                                    f4 = (String) jo.get("field5");
                                    l4 = Integer.parseInt(f4);


                                }


                                if (jo.get("field6") != JSONObject.NULL) {

                                    f5 = (String) jo.get("field6");
                                    l5 = Integer.parseInt(f5);


                                }
                            }




                            String date = jo.getString("created_at");
                            DateFormat format = new SimpleDateFormat("yyyy-mm-dd'T'HH:MM:SS'Z'", Locale.ENGLISH);
                            Date date1 = format.parse(date);
                            Date date2=new Date();

                            if(i == 0){
                                 date2 =date1;

                                series4.resetData(new DataPoint[]{new DataPoint(0,l)});
                                series5.resetData(new DataPoint[]{new DataPoint(0,l1)});
                                series6.resetData(new DataPoint[]{new DataPoint(0,l2)});


                            }
long k = date1.getTime()- date2.getTime();
                            if(k==0){
                                k++;
                            }
                            k = k/1000000l;
                            int d = (int) k;
                            d=d+i;

                           // Toast.makeText(getApplicationContext(), l, Toast.LENGTH_SHORT).show();
                           // Log.d("Json", "onResponse: " + l+date+"   "+d);
                            mDataPoint = new DataPoint(c,l);
                            mDataPoint1 = new DataPoint(c1,l1);
                            mDataPoint2=new DataPoint(c2,l2);
                            values.put(d,l);
                            if(r==true)

                            series4.appendData(mDataPoint,false,100);

                            if(r1==true)
                                series5.appendData(mDataPoint1,false,100);


                            if(r2==true)
                                series6.appendData(mDataPoint2,false,100);



;

                        }
                        callBack.onSuccess();

                        Log.d("TAG00", "fetchData2: "+values.size());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }, new com.android.volley.Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
        public void onSuccess(JSONObject response){

        }
    });



    Volley.newRequestQueue(this).add(objectRequest);


   // mGraphView1.removeAllSeries();
   // mGraphView1.addSeries(series);

    Log.d("TAG00", "fetchData: "+values.size());


}

public void sendData(final String[] s1) throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("field7",s1[0]);

    final JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, "https://api.thingspeak.com/update?api_key=0E1KKMNA5S4BXCOS&field7="+ s1[0], null,

            new com.android.volley.Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.d("resp123", "onResponse: "+response.toString());

                       // if(response.toString()=="0"){
                         //   sendData(s1);
                       // }


                    }


                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new com.android.volley.Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            Log.d("senderror", "onErrorResponse: "+error.toString());

        }
        public void onSuccess(JSONObject response){

        }
    });

    JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, "https://api.thingspeak.com/update?api_key=0E1KKMNA5S4BXCOS&field7="+ s1[0], null,
            new Response.Listener<JSONArray>()
            {
                @Override
                public void onResponse(JSONArray response) {
                    // display response
                    Log.d("Response", response.toString());
                }
            },
            new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error.Response", error.toString());
                }
            }
    );


    Volley.newRequestQueue(this).add(getRequest);


}


    protected void sendJson(final String field7) throws JSONException {

    Date d = new Date();
    int i =0;

  final  String URL = "https://api.thingspeak.com/channels/528079/bulk_update.json";
  final JSONArray updates = new JSONArray();
  final JSONObject finaljson = new JSONObject();
  final  JSONObject enterjson = new JSONObject();
  enterjson.put("delta_t",4);
  finaljson.put("write_api_key","0E1KKMNA5S4BXCOS");

        Thread t = new Thread() {

            public void run() {
                Looper.prepare(); //For Preparing Message Pool for the child Thread
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 100000); //Timeout Limit
                org.apache.http.HttpResponse response;
                JSONObject json = new JSONObject();


                try {
                    HttpPost post = new HttpPost(URL);
                    enterjson.put("field7", field7);

                    updates.put(enterjson);
                    finaljson.put("updates",updates);

                    StringEntity se = new StringEntity( finaljson.toString());
                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setEntity(se);
                    Log.d("senddata", "run: ");
                    response = client.execute(post);
                    HttpEntity resEntity = response.getEntity();

                    String _response=EntityUtils.toString(resEntity);


                    /*Checking response */
                    if(response!=null){
                       // InputStream in = response.getEntity().getContent(); //Get the data in the entity
                        Log.d("senddate", _response);
                    }
                    if(!_response.equals("{\"success\":true}")){

                        Log.d("check", "doing again");

                        mSwitch1.setEnabled(false);
                        sleep(1000);
                        sendJson(field7);
                    }
                    else{

                        Log.d("check", "done");
                        mSwitch1.setEnabled(true);
                        Toast.makeText(MainActivity.this, "Response received now",
                                Toast.LENGTH_LONG).show();
                    }

                } catch(Exception e) {
                    e.printStackTrace();
                   // createDialog("Error", "Cannot Estabilish Connection");
                }

               // Looper.loop(); //Loop in the message queue
            }
        };

        t.start();
    }
        }







