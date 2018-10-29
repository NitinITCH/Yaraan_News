package com.app.yaraan.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.yaraan.activities.Constants;
import com.app.yaraan.activities.DialogUtils;

import com.app.yaraan.R;
//import com.app.yaraan.htmltowebview.URLImageParser;
import com.app.yaraan.activities.LandingAct;
import com.app.yaraan.models.GetDetail;
import com.app.yaraan.retrofit.RestClient;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.share.model.ShareLinkContent;

import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 29/3/18.
 */

public class NewsDetail extends RecyclerView.Adapter<NewsDetail.MyViewHolder> {

    Context context;
    ArrayList<GetDetail>getDetailArrayList=new ArrayList<>();
    public NewsDetail(Context context, ArrayList<GetDetail>getDetailArrayList){

        this.context=context;
        this.getDetailArrayList=getDetailArrayList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.get_detail_webview_data,parent,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.sharedPreferences=context.getSharedPreferences(Constants.KEY,Context.MODE_PRIVATE);

        String liks= holder.sharedPreferences.getString("likes","");
        final String comments=holder.sharedPreferences.getString("comment","");
        String head=getDetailArrayList.get(position).getTitle();


        if(!TextUtils.isEmpty(liks)) {

            holder.txtnewsLikeHead.setText(getDetailArrayList.get(position).getNewLikes());
        }else {
            holder.txtnewsLikeHead.setText("0");
        }
        holder.txtCommentHead.setText(getDetailArrayList.get(position).getCommentCount());
//        holder.txtHeadLike.setText(liks);
//        holder.txtComment.setText(comments);
        holder.spannableString = new SpannableString(head);
//        holder.spannableString.setSpan(new RelativeSizeSpan(0.9f),0,98,0);
//        holder.spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorText)),0,98,
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        Typeface typeface=Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
        Typeface typeface2=Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");


      if(!TextUtils.isEmpty(getDetailArrayList.get(position).getStatus()) && getDetailArrayList.get(position).getStatus().equals("1")){
          holder.imgFavorite.setSelected(true);

      }else {
          holder.imgFavorite.setSelected(false);
      }
        String desc=getDetailArrayList.get(position).getDescription();

        String image_string=getDetailArrayList.get(position).getImage();

        String authorImage=getDetailArrayList.get(position).getAuthorImage();

        String image_full_url=Constants.BASE_IMAGE+image_string;

        String author_get_Image=Constants.BASE_IMAGE+authorImage;

        Glide.with(context).load(author_get_Image).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.authorStImage);
        Glide.with(context).load(image_full_url).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imgHead);

        holder.txtStatus.setText(getDetailArrayList.get(position).getAuthorName());
        holder.txtClock.setText(getDetailArrayList.get(position).getTime());
        holder.txtHeadMain.setText(head);
        holder.txtHeadMain.setTypeface(typeface);

//        URLImageParser parse=new URLImageParser(holder.webview,context);
//
//
//        Spanned html=Html.fromHtml(desc,parse,null);

        String plainText = String.valueOf(Html.fromHtml(desc));




//        String dtaEncode= String.valueOf(html);

        WebSettings webSettings=holder.webview.getSettings();

        webSettings.setFixedFontFamily(String.valueOf(typeface2));
//        holder.webview.getSettings().setAppCacheMaxSize( 10 * 1024 * 1024 ); // 10MB
//        holder.webview.getSettings().setLoadsImagesAutomatically(true);
//        holder.webview.getSettings().setAppCachePath(context.getCacheDir().getAbsolutePath() );
        holder.webview.getSettings().setAllowFileAccess( true );
//        holder.webview.getSettings().setAppCacheEnabled( true );
        holder.webview.getSettings().setJavaScriptEnabled( true );
//        holder.webview.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT );
        holder.webview.pageDown(true);
        holder.webview.setVerticalScrollBarEnabled(true);
        holder.webview.setHorizontalScrollBarEnabled(false);
        holder.webview.setWebChromeClient(new WebChromeClient());

        holder.webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                super.shouldOverrideUrlLoading(view, url);
                return false;
            }

            @Override
            public void onPageFinished(final WebView view, final String url) {
                super.onPageFinished(view, url);
                holder.webview.requestLayout();
            }

        });
//        holder.webview.loadData(getDetailArrayList.get(position).getDescription(),"text/html;charset=utf-8","base64");
        holder.webview.loadDataWithBaseURL( null,"<html dir=\"rtl\" lang=\"ps\"><body>" +getDetailArrayList.get(position).getDescription() + "</body></html>", "text/html;charset=utf-8", "base64", null);;

        MobileAds.initialize(context, "ca-app-pub-3934986051148024~515468367");

        AdRequest adRequest = new AdRequest.Builder().build();
        holder.adView1.loadAd(adRequest);

        AdRequest adRequest1 = new AdRequest.Builder().build();
        holder.adView2.loadAd(adRequest1);
    }

    @Override
    public int getItemCount() {

        return getDetailArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        AdView adView1, adView2;

        ImageView imgHead,imgClose,imgFavorite,authorStImage,imgWhatsup;
        CollapsingToolbarLayout collapsingToolbarLayout;
        String masterId;
        Context mContext;

        ImageView imgFacebook;

        WebView webview;
        SpannableString spannableString,spannablSecond;
        SharedPreferences sharedPreferences;
        ShareDialog shareDialog;
        TextView txtStatus,txtnewsLikeHead,txtCommentHead,txtHeadMain,txtClock,txtHeadLike,txtComment,txtShare;
        public MyViewHolder(View itemView) {
            super(itemView);

            shareDialog=new ShareDialog((Activity) context);

            imgFacebook=(ImageView) itemView.findViewById(R.id.imgFacebook);

            final String unstatus="2";

            final String linkShare=getDetailArrayList.get(getLayoutPosition()+1).getMasterId();
            final String url="http://app.hindara.com/api/web.php?newId="+linkShare;

            imgFacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ShareLinkContent content =
                            new ShareLinkContent.Builder()
                                    .setContentUrl(
                                            Uri.parse(url))
                                    .build();


                    shareDialog.show(content);
                }
            });

            imgWhatsup=(ImageView)itemView.findViewById(R.id.imgWhatsup);

            imgWhatsup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.setPackage("com.whatsapp");
                    intent.putExtra(Intent.EXTRA_TEXT, url);
                    context.startActivity(intent);
                }
            });

//            imgFacebook.setShareContent(content);
            imgHead=(ImageView)itemView.findViewById(R.id.imgHead);
            authorStImage=(ImageView)itemView.findViewById(R.id.authorStImage);
//            imgClose=(ImageView)itemView.findViewById(R.id.imgClose);
            webview=(WebView) itemView.findViewById(R.id.webview);
            webview.setVerticalScrollBarEnabled(true);
            txtnewsLikeHead=(TextView)itemView.findViewById(R.id.txtnewsLikeHead);
            txtCommentHead=(TextView)itemView.findViewById(R.id.txtCommentHead);
            txtHeadMain=(TextView)itemView.findViewById(R.id.txtHeadMain);
            txtClock=(TextView)itemView.findViewById(R.id.txtClock);

            txtStatus=(TextView)itemView.findViewById(R.id.txtStatus);

            imgFavorite=(ImageView)itemView.findViewById(R.id.imgFavorite);

            txtHeadLike=(TextView)itemView.findViewById(R.id.txtHeadLike);
            txtComment=(TextView)itemView.findViewById(R.id.txtComment);

            imgFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    masterId=getDetailArrayList.get(getLayoutPosition()).getMasterId();
                    Log.e("masterid",masterId);
                    String diviceToke=FirebaseInstanceId.getInstance().getToken();
                    String status = imgFavorite.isSelected()?"0":"1";
                    getFavorite(diviceToke,masterId,status,imgFavorite,getLayoutPosition());
                }
            });


//       imgClose.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ((DetailAct)context).onBackPressed();
//                }
//            });

            adView1 = itemView.findViewById(R.id.adView1);
            adView2 = itemView.findViewById(R.id.adView2);
        }

    }


    void getFacebook(String newsId){

        RestClient.getServices().getFacebook(newsId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {

                    String jsonString=response.body().string();
                    JSONObject jsonObject=new JSONObject(jsonString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });



    }

    void getFavorite(String dToken, String newsId, final String status, final ImageView imageView, final int pos){
        DialogUtils.showProgressDialog(context,"Please Wait");
        RestClient.getServices().getFavor(dToken,newsId,status).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String jsonString=response.body().string();
                    JSONObject jsonObject=new JSONObject(jsonString);

                    int succuss=jsonObject.getInt("success");

                    if(succuss==1) {

                       imageView.setSelected(status.equals("1"));
                       getDetailArrayList.get(pos).setStatus(status);
                       notifyDataSetChanged();


                    }
                    DialogUtils.cancleProgressDialog();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context,"Something Went wromg",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context,"Internet Not working",Toast.LENGTH_SHORT).show();

            }
        });

    }
}
