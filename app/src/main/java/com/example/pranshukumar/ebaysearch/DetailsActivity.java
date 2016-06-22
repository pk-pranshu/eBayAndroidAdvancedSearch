package com.example.pranshukumar.ebaysearch;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
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
import android.widget.RelativeLayout;
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
import com.facebook.FacebookSdk;
import android.util.Log;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import org.json.JSONObject;



public class DetailsActivity extends ActionBarActivity {

    Button btn;
    String itemNo;
    String jdata="";
    String imgURL;
    String topURL="";
    Float s;


    CallbackManager callbackManager;
    ShareDialog shareDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

//        FacebookSdk.sdkInitialize(getApplicationContext());

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        // this part is optional
//        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() { ... });

//
//                        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
//
//                            @Override
//                            public void onSuccess(Sharer.Result result) {
//                                Log.d(LOG_TAG, "success");
//                            }
//
//                            @Override
//                            public void onError(FacebookException error) {
//                                Log.d(LOG_TAG, "error");
//                            }
//
//                            @Override
//                            public void onCancel() {
//                                Log.d(LOG_TAG, "cancel");
//                            }
//                        });



        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {


            @Override
            public void onSuccess(Sharer.Result result) {
                Log.d("FB", "success");

                Toast.makeText(DetailsActivity.this,
                        "Posted Successfully", Toast.LENGTH_LONG).show();

            }
//
//            public void onComplete()
//            {
//               Toast.makeText(DetailsActivity.this,
//                        "Post Successfull", Toast.LENGTH_LONG).show();
//            }
            @Override
            public void onError(FacebookException error) {
                Log.d("FB", "error");
                Toast.makeText(DetailsActivity.this,
                        "Sorry Error", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Log.d("FB", "cancel");
                Toast.makeText(DetailsActivity.this,
                        "Cancelled", Toast.LENGTH_LONG).show();
            }

        });



        try{

            itemNo=getIntent().getStringExtra("itemno");
            jdata=getIntent().getStringExtra("jsondata");


            ImageView img=(ImageView) findViewById(R.id.imageView);
            TextView tit=(TextView) findViewById(R.id.textView6);
            TextView pr=(TextView) findViewById(R.id.textView7);
            TextView loc=(TextView) findViewById(R.id.textView8);
            ImageView imgtop=(ImageView) findViewById(R.id.imageView6);

            TextView category=(TextView) findViewById(R.id.textView13);
            TextView cond=(TextView) findViewById(R.id.textView14);
            TextView format=(TextView) findViewById(R.id.textView15);

            TextView username=(TextView) findViewById(R.id.textView19);
            TextView feedbackscore=(TextView) findViewById(R.id.textView20);
            TextView positivefeedback=(TextView) findViewById(R.id.textView21);
            TextView feedbackrating=(TextView) findViewById(R.id.textView23);

            TextView topratedtrue=(TextView) findViewById(R.id.tick1);
            TextView topratedfalse=(TextView) findViewById(R.id.cross1);

            TextView store=(TextView) findViewById(R.id.textView27);

            TextView shiptype=(TextView) findViewById(R.id.textView29);
            TextView handTime=(TextView) findViewById(R.id.textView31);
            TextView shipLocations=(TextView) findViewById(R.id.textView33);

            TextView expeditedtrue=(TextView) findViewById(R.id.tick2);
            TextView expeditedfalse=(TextView) findViewById(R.id.cross2);

            TextView oneDayShippingtrue=(TextView) findViewById(R.id.tick3);
            TextView oneDayShippingfalse=(TextView) findViewById(R.id.cross3);

            TextView returnstrue=(TextView) findViewById(R.id.tick4);
            TextView returnsfalse=(TextView) findViewById(R.id.cross4);



            final JSONObject jobj = new JSONObject(jdata);

            String title = jobj.getJSONObject(itemNo).getJSONObject("basicInfo").getString("title");
            String pictureSuper = jobj.getJSONObject(itemNo).getJSONObject("basicInfo").getString("pictureURLSuperSize");
            String galleryURL = jobj.getJSONObject(itemNo).getJSONObject("basicInfo").getString("galleryURL");
            String Price = jobj.getJSONObject(itemNo).getJSONObject("basicInfo").getString("convertedCurrentPrice");
            String ShippingCost=jobj.getJSONObject(itemNo).getJSONObject("basicInfo").getString("shippingServiceCost");
            String location= jobj.getJSONObject(itemNo).getJSONObject("basicInfo").getString("location");
            String topRatedListing=jobj.getJSONObject(itemNo).getJSONObject("basicInfo").getString("topRatedListing");



            String CategoryName=jobj.getJSONObject(itemNo).getJSONObject("basicInfo").getString("categoryName");
            String Condition=jobj.getJSONObject(itemNo).getJSONObject("basicInfo").getString("conditionDisplayName");
            String BuyingFormat=jobj.getJSONObject(itemNo).getJSONObject("basicInfo").getString("listingType");


            String UserName=jobj.getJSONObject(itemNo).getJSONObject("sellerInfo").getString("sellerUserName");
            String feedbackScore=jobj.getJSONObject(itemNo).getJSONObject("sellerInfo").getString("feedbackScore");
            String positiveFeedbackPercent=jobj.getJSONObject(itemNo).getJSONObject("sellerInfo").getString("positiveFeedbackPercent");
            String feedbackRatingStar=jobj.getJSONObject(itemNo).getJSONObject("sellerInfo").getString("feedbackRatingStar");
            String topRatedSeller=jobj.getJSONObject(itemNo).getJSONObject("sellerInfo").getString("topRatedSeller");
            String sellerStoreName=jobj.getJSONObject(itemNo).getJSONObject("sellerInfo").getString("sellerStoreName");

            String shippingType=jobj.getJSONObject(itemNo).getJSONObject("shippingInfo").getString("shippingType");
            String handlingTime=jobj.getJSONObject(itemNo).getJSONObject("shippingInfo").getString("handlingTime");
            String shipToLocations=jobj.getJSONObject(itemNo).getJSONObject("shippingInfo").getString("shipToLocations");
            String expeditedShipping=jobj.getJSONObject(itemNo).getJSONObject("shippingInfo").getString("expeditedShipping");
            String oneDayShippingAvailable=jobj.getJSONObject(itemNo).getJSONObject("shippingInfo").getString("oneDayShippingAvailable");
            String returnsAccepted=jobj.getJSONObject(itemNo).getJSONObject("shippingInfo").getString("returnsAccepted");



             String finalprice="";

            if(ShippingCost.matches("N/A"))
            {
                ShippingCost="0";
                s=Float.parseFloat(ShippingCost);

            }
            else {
                s = Float.parseFloat(ShippingCost);
            }



            if(s >0)
            {
                finalprice="Price: $"+Price+"( + $"+s+" Shipping)";
                pr.setText("Price: $"+Price+"( + $"+s+" Shipping)");

            }
            else
            {
                finalprice="Price: $"+Price+"(FREE Shipping)";
                pr.setText("Price: $"+Price+"(FREE Shipping)");


            }


            final String fbprice=finalprice;

            tit.setText(title);
            loc.setText(location);




            if(topRatedListing.matches("true"))
            {
                topURL="http://cs-server.usc.edu:45678/hw/hw8/itemTopRated.jpg";
            }


            if(pictureSuper.matches("N/A"))
            {
                imgURL=galleryURL;
            }
            else {
                imgURL=pictureSuper;
            }


            String URL1 =imgURL;
            img.setTag(URL1);
            new DownloadImagesTask().execute(img);


            imgtop.setTag(topURL);
            new DownloadImagesTask().execute(imgtop);


            final String ebaylink=jobj.getJSONObject(itemNo).getJSONObject("basicInfo").getString("viewItemURL");

           btn = (Button)findViewById(R.id.buttonbuynow);

            btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(ebaylink));
                    startActivity(intent);
                }
            });


            final Button button1=(Button)findViewById(R.id.basicinfo);

            final Button button2=(Button)findViewById(R.id.sellerbtn);

            final Button button3=(Button)findViewById(R.id.shippingbtn);



            button2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //when play is clicked show stop button and hide play button
                    RelativeLayout rl1 = (RelativeLayout) findViewById(R.id.callme1);
                    RelativeLayout rl2 = (RelativeLayout) findViewById(R.id.callme2);
                    RelativeLayout rl3=(RelativeLayout) findViewById(R.id.callme3);

//                    button2.setBackgroundDrawable(getResources().getDrawable(R.drawable.abc_btn_check_to_on_mtrl_000));
                    button2.setBackgroundColor(Color.GRAY);
                    button1.setBackgroundResource(android.R.drawable.btn_default);
                    button3.setBackgroundResource(android.R.drawable.btn_default);

                    rl1.setVisibility(View.GONE);
                    rl3.setVisibility(View.GONE);
                    rl2.setVisibility(View.VISIBLE);

                }
            });

            button3.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //when play is clicked show stop button and hide play button
                    RelativeLayout rl1 = (RelativeLayout) findViewById(R.id.callme1);
                    RelativeLayout rl2 = (RelativeLayout) findViewById(R.id.callme2);
                    RelativeLayout rl3=(RelativeLayout) findViewById(R.id.callme3);

                    button3.setBackgroundColor(Color.GRAY);
                    button1.setBackgroundResource(android.R.drawable.btn_default);
                    button2.setBackgroundResource(android.R.drawable.btn_default);

                    rl1.setVisibility(View.GONE);
                    rl2.setVisibility(View.GONE);
                    rl3.setVisibility(View.VISIBLE);

                }
            });

            button1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //when play is clicked show stop button and hide play button
                    RelativeLayout rl1 = (RelativeLayout) findViewById(R.id.callme1);
                    RelativeLayout rl2 = (RelativeLayout) findViewById(R.id.callme2);
                    RelativeLayout rl3=(RelativeLayout) findViewById(R.id.callme3);

                    button1.setBackgroundColor(Color.GRAY);
                    button2.setBackgroundResource(android.R.drawable.btn_default);
                    button3.setBackgroundResource(android.R.drawable.btn_default);

                    rl2.setVisibility(View.GONE);
                    rl3.setVisibility(View.GONE);
                    rl1.setVisibility(View.VISIBLE);

                }
            });



            category.setText(CategoryName);
            cond.setText(Condition);
            format.setText(BuyingFormat);

            username.setText(UserName);
            feedbackscore.setText(feedbackScore);
            positivefeedback.setText(positiveFeedbackPercent);
            feedbackrating.setText(feedbackRatingStar);
            store.setText(sellerStoreName);

            if(topRatedSeller.matches("false"))
            {
                topratedtrue.setVisibility(View.GONE);
            }
            else
            {
                topratedfalse.setVisibility(View.GONE);
                topratedtrue.setVisibility(View.VISIBLE);


            }


            shiptype.setText(shippingType);
            handTime.setText(handlingTime);
            shipLocations.setText(shipToLocations);

            if(expeditedShipping.matches("false"))
            {
                expeditedtrue.setVisibility(View.GONE);
            }
            else
            {
                expeditedfalse.setVisibility(View.GONE);
                expeditedtrue.setVisibility(View.VISIBLE);

            }

            if(oneDayShippingAvailable.matches("false"))
            {
                oneDayShippingtrue.setVisibility(View.GONE);
            }
            else
            {
                oneDayShippingfalse.setVisibility(View.GONE);
                oneDayShippingtrue.setVisibility(View.VISIBLE);

            }

            if(returnsAccepted.matches("false"))
            {
                returnstrue.setVisibility(View.GONE);
            }
            else
            {
                returnsfalse.setVisibility(View.GONE);
                returnstrue.setVisibility(View.VISIBLE);

            }



            ImageButton Fbpost = (ImageButton)findViewById(R.id.fbbutton);

            Fbpost.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){

                    try {
                        String title = jobj.getJSONObject(itemNo).getJSONObject("basicInfo").getString("title");
                        String URL = jobj.getJSONObject(itemNo).getJSONObject("basicInfo").getString("viewItemURL");



                        if (ShareDialog.canShow(ShareLinkContent.class)) {
                            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                    .setContentTitle(title)
                                    .setContentDescription(fbprice)
                                    .setContentUrl(Uri.parse(URL))
                                    .build();

                            shareDialog.show(linkContent);

                        }
                    }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }



//
//                        FacebookSdk.sdkInitialize(getApplicationContext());
//                        callbackManager = CallbackManager.Factory.create();
//                        final ShareDialog shareDialog = new ShareDialog(this);
//
//                        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
//
//                            @Override
//                            public void onSuccess(Sharer.Result result) {
//                                Log.d(LOG_TAG, "success");
//                            }
//
//                            @Override
//                            public void onError(FacebookException error) {
//                                Log.d(LOG_TAG, "error");
//                            }
//
//                            @Override
//                            public void onCancel() {
//                                Log.d(LOG_TAG, "cancel");
//                            }
//                        });
//
//
//                        if (shareDialog.canShow(ShareLinkContent.class)) {
//
//                            ShareLinkContent linkContent = new ShareLinkContent.Builder()
//                                    .setContentTitle("Game Result Highscore")
//                                    .setContentDescription("My new highscore is !!")
//                                    .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=de.ginkoboy.flashcards"))
//
//                                    .setImageUrl(Uri.parse("http://bagpiper-andy.de/bilder/dudelsack%20app.png"))
//                                    .build();
//
//                            shareDialog.show(linkContent);
//                        }
                    }


            });


        }

        catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
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
