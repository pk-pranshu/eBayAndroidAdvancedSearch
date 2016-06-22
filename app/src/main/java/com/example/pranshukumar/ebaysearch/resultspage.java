package com.example.pranshukumar.ebaysearch;

import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
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
import org.json.JSONArray;
import org.json.JSONException;
import android.widget.ImageView;
import org.json.JSONObject;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStream;




public class resultspage extends Activity {



    ClientServerInterface clientServerInterface = new ClientServerInterface();

    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;

    TextView textView11;
    TextView textView22;
    TextView textView33;
    TextView textView44;
    TextView textView55;


    String title1;
    String title2;
    String title3;
    String title4;
    String title5;


    String galleryURL1;
    String galleryURL2;
    String galleryURL3;
    String galleryURL4;
    String galleryURL5;

    String Price1;
    String Price2;
    String Price3;
    String Price4;
    String Price5;


    String ShippingCost1;
    String ShippingCost2;
    String ShippingCost3;
    String ShippingCost4;
    String ShippingCost5;

    Float s1,s2,s3,s4,s5;

    String ebaylink1;
    String ebaylink2;
    String ebaylink3;
    String ebaylink4;
    String ebaylink5;




     String js;

    String outlet_no;
    String headerkey="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultspage);

        new RetreiveData().execute();




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resultspage, menu);
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

    class RetreiveData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub

            EditText TestKey;
            EditText Lowerval;
            EditText Upperval;

            TestKey = (EditText) findViewById(R.id.keyword);

            outlet_no = getIntent().getStringExtra("searchString");
            headerkey=outlet_no;

            outlet_no = outlet_no.replace(" ", "+");


            String outlet_no1 = getIntent().getStringExtra("searchlowp");

            String outlet_no2 = getIntent().getStringExtra("searchup");


            int outlet_no3 = getIntent().getIntExtra("spin", 0);


            String Pricehighest = "";

            String finalspinvalue = "";

            if (outlet_no3 == 0) {
                finalspinvalue = "BestMatch";
            } else if (outlet_no3 == 1) {
                finalspinvalue = "CurrentPriceHighest";
            } else if (outlet_no3 == 2) {
                finalspinvalue = "PricePlusShippingHighest";
            } else if (outlet_no3 == 3) {
                finalspinvalue = "PricePlusShippingLowest";
            }


            try {


                String TestURL = "http://cs-server.usc.edu:24181/android.php?keywords=" + outlet_no + "&lower=" + outlet_no1 + "&upper=" + outlet_no2 + "&sort=" + finalspinvalue;

                js = clientServerInterface.makeHttpRequest(TestURL);




            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return js;
        }

        protected void onPostExecute(String jdata) {


            try {
                final String j=jdata;

                JSONObject jobj = new JSONObject(jdata);

//                final String jdata=js;

                TextView result=(TextView) findViewById(R.id.resultheader);
                result.setText("Results for '"+headerkey+"'");


                galleryURL1 = jobj.getJSONObject("item0").getJSONObject("basicInfo").getString("galleryURL");
                galleryURL2 = jobj.getJSONObject("item1").getJSONObject("basicInfo").getString("galleryURL");
                galleryURL3 = jobj.getJSONObject("item2").getJSONObject("basicInfo").getString("galleryURL");
                galleryURL4 = jobj.getJSONObject("item3").getJSONObject("basicInfo").getString("galleryURL");
                galleryURL5 = jobj.getJSONObject("item4").getJSONObject("basicInfo").getString("galleryURL");



                ImageView mChart1 = (ImageView) findViewById(R.id.imageView1);
                ImageView mChart2= (ImageView) findViewById(R.id.imageView2);
                ImageView mChart3 = (ImageView) findViewById(R.id.imageView3);
                ImageView mChart4= (ImageView) findViewById(R.id.imageView4);
                ImageView mChart5 = (ImageView) findViewById(R.id.imageView5);

                String URL1 =galleryURL1;
                String URL2 =galleryURL2;
                String URL3 =galleryURL3;
                String URL4 =galleryURL4;
                String URL5 =galleryURL5;


                mChart1.setTag(URL1);
                mChart2.setTag(URL2);
                mChart3.setTag(URL3);
                mChart4.setTag(URL4);
                mChart5.setTag(URL5);
                new DownloadImagesTask().execute(mChart1);
                new DownloadImagesTask().execute(mChart2);
                new DownloadImagesTask().execute(mChart3);
                new DownloadImagesTask().execute(mChart4);
                new DownloadImagesTask().execute(mChart5);


                title1 = jobj.getJSONObject("item0").getJSONObject("basicInfo").getString("title");
                title2 = jobj.getJSONObject("item1").getJSONObject("basicInfo").getString("title");
                title3 = jobj.getJSONObject("item2").getJSONObject("basicInfo").getString("title");
                title4 = jobj.getJSONObject("item3").getJSONObject("basicInfo").getString("title");
                title5 = jobj.getJSONObject("item4").getJSONObject("basicInfo").getString("title");



                textView1 = (TextView) findViewById(R.id.textView1);
                textView2 = (TextView) findViewById(R.id.textView2);
                textView3 = (TextView) findViewById(R.id.textView3);
                textView4 = (TextView) findViewById(R.id.textView4);
                textView5 = (TextView) findViewById(R.id.textView5);


                textView1.setText(title1);
                textView2.setText(title2);
                textView3.setText(title3);
                textView4.setText(title4);
                textView5.setText(title5);

                Price1 = jobj.getJSONObject("item0").getJSONObject("basicInfo").getString("convertedCurrentPrice");
                Price2 = jobj.getJSONObject("item1").getJSONObject("basicInfo").getString("convertedCurrentPrice");
                Price3 = jobj.getJSONObject("item2").getJSONObject("basicInfo").getString("convertedCurrentPrice");
                Price4 = jobj.getJSONObject("item3").getJSONObject("basicInfo").getString("convertedCurrentPrice");
                Price5 = jobj.getJSONObject("item4").getJSONObject("basicInfo").getString("convertedCurrentPrice");

                textView11 = (TextView) findViewById(R.id.textView11);
                textView22 = (TextView) findViewById(R.id.textView22);
                textView33 = (TextView) findViewById(R.id.textView33);
                textView44 = (TextView) findViewById(R.id.textView44);
                textView55 = (TextView) findViewById(R.id.textView55);



                ShippingCost1=jobj.getJSONObject("item0").getJSONObject("basicInfo").getString("shippingServiceCost");
                ShippingCost2=jobj.getJSONObject("item1").getJSONObject("basicInfo").getString("shippingServiceCost");
                ShippingCost3=jobj.getJSONObject("item2").getJSONObject("basicInfo").getString("shippingServiceCost");
                ShippingCost4=jobj.getJSONObject("item3").getJSONObject("basicInfo").getString("shippingServiceCost");
                ShippingCost5=jobj.getJSONObject("item4").getJSONObject("basicInfo").getString("shippingServiceCost");



                if(ShippingCost1.matches("N/A"))
                {
                    ShippingCost1="0";
                    s1=Float.parseFloat(ShippingCost1);

                }
                else {
                    s1 = Float.parseFloat(ShippingCost1);
                }

                if(ShippingCost2.matches("N/A"))
                {
                    ShippingCost2="0";
                    s2=Float.parseFloat(ShippingCost2);

                }
                else {
                    s2 = Float.parseFloat(ShippingCost2);
                }
                if(ShippingCost3.matches("N/A"))
                {
                    ShippingCost3="0";
                    s3=Float.parseFloat(ShippingCost3);

                }
                else {
                    s3 = Float.parseFloat(ShippingCost3);
                }
                if(ShippingCost4.matches("N/A"))
                {
                    ShippingCost4="0";
                    s4=Float.parseFloat(ShippingCost4);

                }
                else {
                    s4 = Float.parseFloat(ShippingCost4);
                }
                if(ShippingCost5.matches("N/A"))
                {
                    ShippingCost5="0";
                    s5=Float.parseFloat(ShippingCost5);

                }
                else {
                    s5 = Float.parseFloat(ShippingCost5);
                }



                if(s1 >0)
                {
                  textView11.setText("Price: $"+Price1+"( + $"+s1+" Shipping)");

                }
                else
                {
                    textView11.setText("Price: $"+Price1+"(FREE Shipping)");


                }

                if(s2 >0)
                {
                    textView22.setText("Price: $"+Price2+"( + $"+s2+" Shipping)");

                }
                else
                {
                    textView22.setText("Price: $"+Price2+"(FREE Shipping)");


                }

                if(s3 >0)
                {
                    textView33.setText("Price: $"+Price3+"( + $"+s3+" Shipping)");

                }
                else
                {
                    textView33.setText("Price: $"+Price3+"(FREE Shipping)");


                }

                if(s4 >0)
                {
                    textView44.setText("Price: $"+Price4+"( + $"+s4+" Shipping)");

                }
                else
                {
                    textView44.setText("Price: $"+Price4+"(FREE Shipping)");


                }

                if(s5 >0)
                {
                    textView55.setText("Price: $"+Price5+"( + $"+s5+" Shipping)");

                }
                else
                {
                    textView55.setText("Price: $"+Price5+"(FREE Shipping)");
                    


                }


                ebaylink1=jobj.getJSONObject("item0").getJSONObject("basicInfo").getString("viewItemURL");
                ebaylink2=jobj.getJSONObject("item1").getJSONObject("basicInfo").getString("viewItemURL");
                ebaylink3=jobj.getJSONObject("item2").getJSONObject("basicInfo").getString("viewItemURL");
                ebaylink4=jobj.getJSONObject("item3").getJSONObject("basicInfo").getString("viewItemURL");
                ebaylink5=jobj.getJSONObject("item4").getJSONObject("basicInfo").getString("viewItemURL");

                ImageView img1 = (ImageView) findViewById(R.id.imageView1);

                img1.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse(ebaylink1));
                        startActivity(intent);
                    }
                });

                ImageView img2 = (ImageView) findViewById(R.id.imageView2);

                img2.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse(ebaylink2));
                        startActivity(intent);
                    }
                });
                ImageView img3 = (ImageView) findViewById(R.id.imageView3);

                img3.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse(ebaylink3));
                        startActivity(intent);
                    }
                });
                ImageView img4 = (ImageView) findViewById(R.id.imageView4);

                img4.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse(ebaylink4));
                        startActivity(intent);
                    }
                });
                ImageView img5 = (ImageView) findViewById(R.id.imageView5);

                img5.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse(ebaylink5));
                        startActivity(intent);
                    }
                });


                textView1.setOnClickListener(new View.OnClickListener(){

                    public void onClick(View v)
                    {

                        Intent intent=new Intent(resultspage.this,DetailsActivity.class);

                        intent.putExtra("itemno", "item0");
                        intent.putExtra("jsondata",j);

                        startActivity(intent);
                    }

                });

                textView2.setOnClickListener(new View.OnClickListener(){

                    public void onClick(View v)
                    {
//                        textView11.setText(j);


                        Intent intent=new Intent(resultspage.this,DetailsActivity.class);

                        intent.putExtra("itemno", "item1");
                        intent.putExtra("jsondata",j);

                        startActivity(intent);
                    }

                });

                textView3.setOnClickListener(new View.OnClickListener(){

                    public void onClick(View v)
                    {
//                        textView11.setText(j);


                        Intent intent=new Intent(resultspage.this,DetailsActivity.class);

                        intent.putExtra("itemno", "item2");
                        intent.putExtra("jsondata",j);

                        startActivity(intent);
                    }

                });

                textView4.setOnClickListener(new View.OnClickListener(){

                    public void onClick(View v)
                    {
//                        textView11.setText(j);


                        Intent intent=new Intent(resultspage.this,DetailsActivity.class);

                        intent.putExtra("itemno", "item3");
                        intent.putExtra("jsondata",j);

                        startActivity(intent);
                    }

                });

                textView5.setOnClickListener(new View.OnClickListener(){

                    public void onClick(View v)
                    {
//                        textView11.setText(j);


                        Intent intent=new Intent(resultspage.this,DetailsActivity.class);

                        intent.putExtra("itemno", "item4");
                        intent.putExtra("jsondata",j);

                        startActivity(intent);
                    }

                });




            }



            catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


    public class DownloadImagesTask extends AsyncTask<ImageView, Void, Bitmap> {

        ImageView imageView = null;

        @Override
        protected Bitmap doInBackground(ImageView... imageViews) {
            this.imageView = imageViews[0];
            return download_Image((String) imageView.getTag());
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }


        private Bitmap download_Image(String url) {

            Bitmap bmp =null;
            try{
                URL ulrn = new URL(url);
                HttpURLConnection con = (HttpURLConnection)ulrn.openConnection();
                InputStream is = con.getInputStream();
                bmp = BitmapFactory.decodeStream(is);
                if (null != bmp)
                    return bmp;

            }catch(Exception e){}
            return bmp;

        }
    }
}
