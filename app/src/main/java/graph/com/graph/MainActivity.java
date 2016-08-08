package graph.com.graph;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.formatter.FormattedStringCache;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    LineChart lineChart;
    XAxis xAxis;
    YAxis leftAxis;
    int j=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lineChart = (LineChart) findViewById(R.id.chart1);
        lineChart.setNoDataTextDescription("Either we are fetching data or you aren't connected to internet");

        // enable touch gestures
        lineChart.setTouchEnabled(true);

        lineChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setDrawGridBackground(false);
        lineChart.setHighlightPerDragEnabled(true);

        // set an alternative background color
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.setViewPortOffsets(0f, 0f, 0f, 0f);

        xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        //xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(true);
        xAxis.setTextColor(Color.rgb(255, 192, 56));
        xAxis.setCenterAxisLabels(true);

        leftAxis = lineChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);



        getdata();
    }
    public void getdata(){
        Log.v("S", "Forecast string: 0");
        getChartData getChartData =new getChartData();
        getChartData.execute();
    }
    public class getChartData extends AsyncTask<String,Void,String>{
        //taken it here to test its in various function in

        ArrayList<String> value=new ArrayList<>();
        ArrayList<Long> timestamp;

        protected String doInBackground(String... params) {
            String request = "http://52.77.220.93:4000/getLast?device=thane1&sensor=arduino&lim=300";
            URL url;
            HttpURLConnection connection = null;
            String decodedString ="";
            String returnMsg = "";

            JSONObject myJson=null;
            String JsonStr = null; //used to store json string
            JSONArray jsonArray=null; //used to store json data as json array
            String prsdData[]={""};
            int len;
            Log.v("S", "Forecast string: 2");
            try {
                url = new URL(request);

                //making connection
                connection = (HttpURLConnection) url.openConnection();
                connection.addRequestProperty("Content-Type", "application/json");
                connection.setRequestMethod("GET");
                Log.v("S", "Forecast string: 1" );
                // Read the input stream into a String
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                Log.v("S", "Forecast string: 1" + in);
                while ((decodedString = in.readLine()) != null) {
                    returnMsg += decodedString;
                    myJson = new JSONObject(returnMsg);
                    String str = myJson.optString("data");
                    str = str.substring(1, str.length() - 1);
                    str = str.replace(",", " ");
                    str = str.replace(":", " ");
                    prsdData = str.split("[\"]");
                    len = prsdData.length;
                }
                in.close(); //close stream
            }
            catch(Exception e){}
            //used to close connection in case of an exception
            finally {
                if (connection != null) {
                    connection.disconnect();              //connection.disconnect();
                }
            }
            //used to check what is received as input from url connection
            Log.v("SA", "Forecast string: 3" + returnMsg); //null is r


            return returnMsg;
            // return "{\"device\":\"thane1\",\"sensor\":\"arduino\",\"data\":{\"1467871629\":303,\"1467871661\":303,\"1467871693\":303,\"1467871720\":303,\"1467871755\":303,\"1467871785\":303,\"1467871807\":303,\"1467871838\":303,\"1467871862\":303,\"1467871893\":303,\"1467871925\":303,\"1467871957\":304,\"1467871984\":304,\"1467872016\":304,\"1467872058\":303,\"1467872111\":303,\"1467872142\":303,\"1467872176\":303,\"1467872207\":303,\"1467872239\":303,\"1467872271\":304,\"1467872305\":304,\"1467872332\":304,\"1467872394\":304,\"1467872426\":304,\"1467872458\":304,\"1467872489\":304,\"1467872521\":304,\"1467872553\":304,\"1467872584\":304,\"1467872616\":304,\"1467872648\":304,\"1467872680\":304,\"1467872706\":304,\"1467872734\":304,\"1467872766\":304,\"1467872797\":304,\"1467872829\":304,\"1467872860\":304,\"1467872924\":304,\"1467872955\":304,\"1467872988\":304,\"1467873019\":304,\"1467873051\":304,\"1467873083\":305,\"1467873114\":305,\"1467873146\":305,\"1467873178\":304,\"1467873210\":305,\"1467873241\":305,\"1467873273\":305,\"1467873305\":305,\"1467873336\":305,\"1467873368\":173,\"1467873395\":173,\"1467873427\":207,\"1467873454\":207,\"1467873487\":200,\"1467873517\":215,\"1467873549\":196,\"1467873580\":181,\"1467873612\":218,\"1467873643\":232,\"1467873675\":199,\"1467873706\":207,\"1467873738\":195,\"1467873796\":217,\"1467873861\":253,\"1467873915\":253,\"1467873946\":259,\"1467873978\":266,\"1467874009\":244,\"1467874041\":264,\"1467874072\":254,\"1467874105\":259,\"1467874135\":259,\"1467875243\":158,\"1467875274\":160,\"1467875305\":171,\"1467875337\":213,\"1467875369\":169,\"1467875401\":183,\"1467875431\":183,\"1467875463\":166,\"1467875494\":164,\"1467875526\":162,\"1467875557\":167,\"1467875588\":161,\"1467875620\":158,\"1467875651\":159,\"1467875682\":158,\"1467875714\":157,\"1467875745\":195,\"1467875777\":169,\"1467875808\":172,\"1467875840\":159,\"1467875871\":162,\"1467875903\":156,\"1467875934\":156,\"1467875965\":159,\"1467875997\":158,\"1467876028\":153,\"1467876056\":153,\"1467876087\":163,\"1467876118\":171,\"1467876150\":168,\"1467876181\":156,\"1467876213\":156,\"1467876307\":154,\"1467876338\":155,\"1467876369\":151,\"1467876401\":155,\"1467876432\":153,\"1467876464\":152,\"1467876495\":149,\"1467876526\":148,\"1467876558\":145,\"1467876589\":143,\"1467876617\":143,\"1467876648\":140,\"1467876711\":136,\"1467876743\":134,\"1467876769\":134,\"1467878921\":132,\"1467878948\":132,\"1467879001\":132,\"1467879032\":132,\"1467879064\":132,\"1467879095\":132,\"1467879126\":132,\"1467879158\":132,\"1467879189\":132,\"1467879220\":132,\"1467879252\":132,\"1467879283\":132,\"1467879314\":132,\"1467879346\":132,\"1467879378\":133,\"1467879405\":133,\"1467879436\":132,\"1467879467\":132,\"1467879499\":133,\"1467879530\":133,\"1467879561\":133,\"1467879593\":133,\"1467879871\":133,\"1467879902\":133,\"1467879933\":133,\"1467879996\":133,\"1467880028\":133,\"1467880059\":131,\"1467880090\":129,\"1467880122\":127,\"1467880153\":125,\"1467880184\":124,\"1467880216\":122,\"1467880247\":120,\"1467880278\":118,\"1467880310\":117,\"1467880341\":115,\"1467880372\":113,\"1467880404\":111,\"1467880431\":111,\"1467880462\":108,\"1467880496\":106,\"1467880556\":103,\"1467880588\":100,\"1467880619\":99,\"1467880650\":97,\"1467880681\":95,\"1467880713\":93,\"1467880745\":91,\"1467880772\":91,\"1467880803\":88,\"1467880834\":87,\"1467880865\":85,\"1467880897\":83,\"1467880924\":83,\"1467880955\":80,\"1467880987\":78,\"1467881018\":76,\"1467881049\":74,\"1467881081\":73,\"1467881112\":72,\"1467881143\":72,\"1467881174\":72,\"1467881206\":72,\"1467881399\":73,\"1467881430\":73,\"1467881461\":73,\"1467881494\":73,\"1467881524\":73,\"1467881555\":73,\"1467881587\":73,\"1467881618\":73,\"1467881649\":73,\"1467881680\":73,\"1467881712\":73,\"1467881743\":73,\"1467881774\":73,\"1467881806\":73,\"1467881837\":73,\"1467881864\":73,\"1467881896\":74,\"1467881927\":74,\"1467881958\":74,\"1467881989\":74,\"1467882020\":74,\"1467882053\":74,\"1467882085\":74,\"1467882117\":74,\"1467882149\":74,\"1467882180\":74,\"1467882239\":0,\"1467882297\":0,\"1467882338\":146,\"1467882369\":170,\"1467882401\":168,\"1467882432\":166,\"1467882464\":106,\"1467882495\":75,\"1467882526\":75,\"1467882557\":75,\"1467882588\":75,\"1467882620\":75,\"1467882651\":79,\"1467882683\":88,\"1467882714\":97,\"1467882745\":105,\"1467882776\":113,\"1467882807\":120,\"1467882839\":127,\"1467882870\":134,\"1467882901\":141,\"1467882932\":145,\"1467882964\":150,\"1467882995\":154,\"1467883026\":159,\"1467883058\":163,";

        }
        protected void onPostExecute(String result) {
            // String temp=result;
            //if (!result.equals("")) {
            Log.v("","V");

            //separetes data part and {\"device\":\"thane1\",\"sensor\":\"arduino\",\"data\": in separate tokens
            StringTokenizer stringTokenizer = new StringTokenizer(result, "{");

            //to skip to data tokens
            stringTokenizer.nextToken();
            //separates each timestamp an value into separate tokens
            StringTokenizer stringTokenizer1 = new StringTokenizer(stringTokenizer.nextToken().toString(), ",");
            StringTokenizer stringTokenizer2 = new StringTokenizer("");
            timestamp = new ArrayList<>();
            ArrayList<Float> values = new ArrayList<>();

            for (int i = 0; ; i++) {

                //break if no more token
                if (!stringTokenizer1.hasMoreTokens()) break;
                stringTokenizer2 = new StringTokenizer(stringTokenizer1.nextToken().toString(), ":");


                String str = stringTokenizer2.nextToken();
                timestamp.add(Long.parseLong(str.substring(1, str.length() - 1).trim()));

                //if this is last token
                if (!stringTokenizer1.hasMoreTokens()) {
                    str = stringTokenizer2.nextToken().toString();
                    values.add(Float.parseFloat(str.substring(0, str.length() - 2).trim()));
                    break;
                }
                values.add(Float.parseFloat(stringTokenizer2.nextToken().toString().trim()));
                Log.v("SA", "Forecast string: 3" + i + " " + values.get(i) + "\n" + timestamp.get(i));
            }
            setDate();

            xAxis.setValueFormatter(new AxisValueFormatter() {

                private FormattedStringCache.Generic<Long, Date> mFormattedStringCache = new FormattedStringCache.Generic<>(new SimpleDateFormat("dd MMM HH:mm"));

                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    Long v = (long) value;
                    j++;
                    if (j < timestamp.size() - 1)
                        return mFormattedStringCache.getFormattedValue(new Date(timestamp.get(j) * 1000), v);

                    //couldn't find a way to stop adding labels to x axis so after all time gap are added to avoid error this is added //needs correction
                    else
                        //return mFormattedStringCache.getFormattedValue(new Date(v), v);

                        return "0";
                }

                //@Override
                public int getDecimalDigits() {
                    return 0;
                }
            });

            ArrayList<Entry> entries = new ArrayList<>();
            ArrayList<String> entries1 = new ArrayList<>();

            for (int i = 0; i < values.size(); i++) {
                entries.add(new Entry(values.get(i), i));
                entries1.add(timestamp.get(i).toString());
            }
            ArrayList<Entry> values1 = new ArrayList<Entry>();
            float gap=timestamp.get(1)-timestamp.get(0);
            //float to = now + (100 / 2) * hourMillis;
            int i=2;
            for (float x =Float.parseFloat(timestamp.get(0).toString()); x < timestamp.get(timestamp.size()-1)&&i<timestamp.size()-1; x += gap) {

                float y = values.get(i-2);
                values1.add(new Entry(x, y)); // entry as per timegap
                gap=timestamp.get(i)-timestamp.get(i-1); //gap is changed s per time gap of timestamp
                i++;
            }
            LineDataSet lineDataSet = new LineDataSet(values1, "Project");
            //lineDataSet.setAxisDependency(AxisDependency.LEFT);

            LineData lineData1 = new LineData(lineDataSet);
            //lineDataSet.setDrawCubic(true);
            lineChart.setData(lineData1);
            lineChart.animateY(5000);
            //}
        }
        public void setDate(){
            for(int i=0;i<timestamp.size();i++){
                DateFormat sdf = new SimpleDateFormat("HH:mm");
                Date netDate = new Date(timestamp.get(i)*1000);
                //used to generate hours in a day
                DateFormat sdf1 = new SimpleDateFormat("k");

                int k=Integer.parseInt(sdf1.format(netDate).toString());
                if(k>0&&k<=11)
                    value.add(sdf.format(netDate).toString()+"AM");
                else
                    value.add(sdf.format(netDate).toString() + "PM");
                Log.v("H", sdf.format(netDate)+ " " + k);
            }
        }
    }
}