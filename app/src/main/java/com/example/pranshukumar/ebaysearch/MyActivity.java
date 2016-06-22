package com.example.pranshukumar.ebaysearch;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.app.Activity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;
import android.graphics.Color;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.util.Log;
import android.widget.Toast;
import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;



public class MyActivity extends Activity implements AdapterView.OnItemSelectedListener {

//    private static final String TAG = "Pranshu's Message";


    Spinner spinner;
    Button button;

    Button mySearchBtn;
    Button myClearBtn;

    String outlet_no;
    String headerkey;

    String js;


    String keynoresult;


    EditText key;
    EditText Price_from;
    EditText Price_to;

    JSONObject jobj = null;

    ClientServerInterface clientServerInterface = new ClientServerInterface();
    TextView textView;
    String ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        textView = (TextView) findViewById(R.id.textView);


//        new RetreiveData().execute();


//        EditText TestKey;
//        EditText Lowerval;
//        EditText Upperval;
//
//        TestKey = (EditText) findViewById(R.id.keyword);
//
//        outlet_no = getIntent().getStringExtra("searchString");
//        headerkey = outlet_no;
//
//        outlet_no = outlet_no.replace(" ", "+");
//
//
//        String outlet_no1 = getIntent().getStringExtra("searchlowp");
//
//        String outlet_no2 = getIntent().getStringExtra("searchup");
//
//
//        int outlet_no3 = getIntent().getIntExtra("spin", 0);
//
//
//        String Pricehighest = "";
//
//        String finalspinvalue = "";
//
//        if (outlet_no3 == 0) {
//            finalspinvalue = "BestMatch";
//        } else if (outlet_no3 == 1) {
//            finalspinvalue = "CurrentPriceHighest";
//        } else if (outlet_no3 == 2) {
//            finalspinvalue = "PricePlusShippingHighest";
//        } else if (outlet_no3 == 3) {
//            finalspinvalue = "PricePlusShippingLowest";
//        }
//
//
//        try {
//
//
//            String TestURL = "http://mansinegi.byethost24.com/testAnd.php?keywords=" + outlet_no + "&lower=" + outlet_no1 + "&upper=" + outlet_no2 + "&sort=" + finalspinvalue;
//
//            js = clientServerInterface.makeHttpRequest(TestURL);
//
//            JSONObject jobj = new JSONObject(js);
//            TextView txt = (TextView) findViewById(R.id.test);
//
//
//            keynoresult = jobj.getString("ack");
//
//            if (keynoresult.matches("No Results Found")) {
//                txt.setText("NO RESULTS FOUND");
//            }
//
//
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

//        Log.i(TAG,"onCreate");

//        button = (Button) findViewById(R.id.search_button);




        mySearchBtn = (Button) findViewById(R.id.search_button);
        myClearBtn = (Button) findViewById(R.id.clear);

        spinner = (Spinner) findViewById(R.id.spinner);

        key = (EditText) findViewById(R.id.keyword);


        Price_from = (EditText) findViewById(R.id.LowerPrice);

        Price_to = (EditText) findViewById(R.id.UpperPrice);


        mySearchBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                TextView txt = (TextView) findViewById(R.id.test);
                txt.setVisibility(View.VISIBLE);


//
//                String TestURL = "http://mansinegi.byethost24.com/testAnd.php?keywords=" + key + "&lower=" + Price_from + "&upper=" + Price_to + "&sort=" + spinner;
//
//            js = clientServerInterface.makeHttpRequest(TestURL);
//
//                try {
//                    JSONObject jobj = new JSONObject(js);
//                    txt.setText("NO RESULTS FOUND");
//
//                    keynoresult = jobj.getString("ack");
//
//                    if (keynoresult.matches("No Results Found")) {
//                        txt.setText("NO RESULTS FOUND");
//
//                    }
//                }
//                    catch(Exception e)
//                    {
//                                        e.printStackTrace();
//
//                    }
//                TextView txt = (TextView) findViewById(R.id.test);



                final String finalkey = key.getText().toString();
                final String lowprice = Price_from.getText().toString();
                final String upprice = Price_to.getText().toString();

                if (finalkey.matches("")) {
                    key.requestFocus();
                    txt.setTextColor(Color.RED);
                    txt.setText("Please enter a Keyword !");
                } else {


                    float lp = 0;
                    float up = 0;

                    if (!(lowprice.matches(""))) {

                        lp = Float.parseFloat(lowprice);
                    }
                    if (!(upprice.matches(""))) {

                        up = Float.parseFloat(upprice);
                    }


                    if (lp < 0) {
                        Price_from.requestFocus();
                        txt.setText("Price can only be a positive number!");
                        txt.setTextColor(Color.RED);
                    } else if (up < 0) {
                        Price_to.requestFocus();
                        txt.setText("Price can only be a positive number!");
                        txt.setTextColor(Color.RED);

                    }
//                  else {
//                        tetstcall();
////                        txt.setTextColor(Color.BLUE);
////                        txt.setText("Clicked!");
//                    }


                    else if (!(lowprice.matches("")) && !(upprice.matches(""))) {

                        if (lp > up) {
                            txt.setText("Price From should be less than or equal to Price To ");
                            txt.setTextColor(Color.RED);
                        } else {
                            tetstcall();
                        }

                    } else {
                        tetstcall();
                    }

                }

            }
        });


//
//        mySearchBtn.setOnClickListener(new Button.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//
//              startActivity(new Intent(getApplicationContext(),searchresults.class));
//            }});

        myClearBtn.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                TextView txt = (TextView) findViewById(R.id.test);
                key.getText().clear();
                Price_from.getText().clear();
                Price_to.getText().clear();
                spinner.setSelection(0);
                txt.setVisibility(View.GONE);
            }
        });


        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.sort, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void tetstcall() {

        Intent myIntent = new Intent(MyActivity.this, resultspage.class);

        String outlet_no = key.getText().toString();

        String outlet_no1 = Price_from.getText().toString();

        String outlet_no2 = Price_to.getText().toString();

//    String outlet_no3= spinner.getSelectedItem().toString();

        Integer outlet_no3 = spinner.getSelectedItemPosition();

        myIntent.putExtra("searchString", outlet_no);
        myIntent.putExtra("searchlowp", outlet_no1);
        myIntent.putExtra("searchup", outlet_no2);
        myIntent.putExtra("spin", outlet_no3);
        TextView txt = (TextView) findViewById(R.id.test);
        txt.setVisibility(View.GONE);

        startActivity(myIntent);

//    startActivity(new Intent(getApplicationContext(),resultspage.class));

//    TextView check;
//    check = (TextView) findViewById(R.id.test);
//    check.setText("hello");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView myText = (TextView) view;
//        Toast.makeText(this,"YouSelected"+myText.getText(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

//    class RetreiveData extends AsyncTask<String, String, String> {
//
//        @Override
//        protected String doInBackground(String... arg0) {
//            // TODO Auto-generated method stub
//
//
//            EditText TestKey;
//            EditText Lowerval;
//            EditText Upperval;
//
//            TestKey = (EditText) findViewById(R.id.keyword);
//
//            outlet_no = getIntent().getStringExtra("searchString");
//            headerkey = outlet_no;
//
//            outlet_no = outlet_no.replace(" ", "+");
//
//
//            String outlet_no1 = getIntent().getStringExtra("searchlowp");
//
//            String outlet_no2 = getIntent().getStringExtra("searchup");
//
//
//            int outlet_no3 = getIntent().getIntExtra("spin", 0);
//
//
//            String Pricehighest = "";
//
//            String finalspinvalue = "";
//
//            if (outlet_no3 == 0) {
//                finalspinvalue = "BestMatch";
//            } else if (outlet_no3 == 1) {
//                finalspinvalue = "CurrentPriceHighest";
//            } else if (outlet_no3 == 2) {
//                finalspinvalue = "PricePlusShippingHighest";
//            } else if (outlet_no3 == 3) {
//                finalspinvalue = "PricePlusShippingLowest";
//            }
//
//
//            try {
//
//
//                String TestURL = "http://mansinegi.byethost24.com/testAnd.php?keywords=" + outlet_no + "&lower=" + outlet_no1 + "&upper=" + outlet_no2 + "&sort=" + finalspinvalue;
//
//                js = clientServerInterface.makeHttpRequest(TestURL);
//
//                JSONObject jobj = new JSONObject(js);
//                TextView txt = (TextView) findViewById(R.id.test);
//
//
//                keynoresult = jobj.getString("ack");
//
//                if (keynoresult.matches("No Results Found")) {
//                    txt.setText("NO RESULTS FOUND");
//                }
//
//
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        }

    }
