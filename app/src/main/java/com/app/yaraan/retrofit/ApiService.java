package com.app.yaraan.retrofit;





import android.support.annotation.Nullable;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

import static com.facebook.HttpMethod.POST;

/**
 * Created by Lovepreet on 4/8/17.
 */

public interface ApiService {
//    @Headers({
//            "Content-Type: application/json",
//            "Authorization:key=AIzaSyDY_ehfc-neQLLtAncA-5znBHHjpxHLmXU"
//    })
//    @POST("send")
//    Call<ResponseBody> sendPushMesage(@Header("Content-Type") String appType, @Header("Authorization") String auth, @Body NotificationPojo notifObject);
    @POST("index.php?action=fb_signup")
    @FormUrlEncoded
    Call<ResponseBody>signUp(@Field("user_name") String userName, @Field("user_email") String email, @Field("fb_id") String fbId,@Field("fb_image")String fbImage,@Field("login_os") String loginos,@Field("user_device_token")String toke);

    @POST("index.php?action=yaraan_user_sign")
    @FormUrlEncoded
    Call<ResponseBody>signupOtp(@Field("user_email") String userEmail,@Field("user_name") String firstName,@Field("user_password")String userLastname,@Field("user_device_token") String userDeviceToken,@Field("login_os")String loginOs);

    // http://allappshere.in/ryder/yaraan/index.php?action=yaraan_user_otp&user_id=3&user_otp=8585
    @POST("index.php?action=yaraan_user_otp")
    @FormUrlEncoded
    Call<ResponseBody>otpR(@Field("user_email")String userid,@Field("user_otp")String userotp);

    //resend Otp
    @POST("index.php?action=yaraan_user_resend_otp&user_email")
    @FormUrlEncoded
    Call<ResponseBody>orpResend(@Field("user_email")String userEmail);

    //email veryfication
    @POST("index.php?action=yaraan_checkemail")
    @FormUrlEncoded
    Call<ResponseBody>emailVerification(@Field("user_email")String user_email,@Field("user_device_token")String token,@Field("login_os")String loginos);

    //image upload
    @Multipart
    @POST("index.php?action=user_image")
    Call<ResponseBody>imageUpload(@Part MultipartBody.Part image , @Part("id")RequestBody id);


    //http://allappshere.in/ryder/yaraan/index.php?action=yarran_content&type=politics&page_number=0
    @POST("index.php?action=yarran_content")
    @FormUrlEncoded
    Call<ResponseBody>getnews(@Field("type")String type,@Field("page_number")int pagnum) ;

    //http://allappshere.in/ryder/yaraan/index.php?action=yarran_content_details&details_id=68
    @POST("index.php?action=yarran_content_details")
    @FormUrlEncoded
    Call<ResponseBody>getDetail(@Field("details_id")String id,@Field("user_device_token")String userToken);


    // http://allappshere.in/ryder/yaraan/index.php?action=yaraan_like&user_id=6&likes_by=7&like_status=1
    @POST("index.php?action=yaraan_like")
    @FormUrlEncoded
    Call<ResponseBody>getLike(@Field("user_id")String userid,@Field("likes_by")String likeBy,@Field("like_status")String likestatus);

    //allappshere.in/ryder/yaraan/index.php?action=yarran_like_status&user_id=101&new_id=62
    @POST("index.php?action=yarran_like_status")
    @FormUrlEncoded
    Call<ResponseBody>getLikeStatus(@Field("user_id")String userid,@Field("new_id")String newsId);


    // http://allappshere.in/ryder/yaraan/index.php?action=yarran_header
    @POST("index.php?action=yarran_header")
    Call<ResponseBody>getHeader();

    //Latest home news
    //http://allappshere.in/ryder/yaraan/index.php?action=yarran_home_latest_news
    @GET("index.php?action=yarran_home_latest_news")
    Call<ResponseBody>getLatestNews();


    // http://allappshere.in/ryder/yaraan/index.php?action=get_notifications
    @GET("index.php?action=get_notifications")
    Call<ResponseBody>getNotification();

    //http://allappshere.in/ryder/yaraan/index.php?action=yaraan_favorite&user_devicetoken=423423423&new_id=55&status=0


    @POST("index.php?action=yaraan_favorite")
    @FormUrlEncoded
    Call<ResponseBody>getFavor(@Field("user_devicetoken")String deviceTok,@Field("new_id")String newsId,@Nullable @Field("status")String status);


    //http://allappshere.in/ryder/yaraan/index.php?action=get_favorite&token=b27ec33b1da4f6a2e61dd1f9ee24f6f7c57f77483ef6994f0c847f00c1af54cc
    @POST("index.php?action=get_favorite")
    @FormUrlEncoded
    Call<ResponseBody>getFavorValues(@Field("token")String token);


    //http://allappshere.in/ryder/yaraan/index.php?action=yaraan_comments&user_id=6&commentby=7&comment=kkkkkkkk
    @POST("index.php?action=yaraan_comments")
    @FormUrlEncoded
    Call<ResponseBody>getComments(@Field("user_id")String userId,@Field("commentby")String commentBy,@Field("comment")String comment);


    //http://allappshere.in/ryder/yaraan/index.php?action=GetComments&new_id=63
    //comments Listing
    @POST("index.php?action=GetComments")
    @FormUrlEncoded
    Call<ResponseBody>getCommentList(@Field("new_id")String newsId);

    //"http://allappshere.in/ryder/yaraan/web.php?newId="
    @POST("web.php?")
    @FormUrlEncoded
    Call<ResponseBody>getFacebook(@Field("newId")String newsid);



    // http://allappshere.in/ryder/yaraan/index.php?action=device_token&device_token=@%&login_os=Android
    @POST("index.php?action=device_token")
    @FormUrlEncoded
    Call<ResponseBody>deviceToken(@Field("device_token")String deviceT,@Field("login_os")String loginOs);

}
