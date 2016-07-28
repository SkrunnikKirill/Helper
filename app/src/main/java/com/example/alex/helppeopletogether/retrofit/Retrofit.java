package com.example.alex.helppeopletogether.retrofit;

import com.example.alex.helppeopletogether.SupportClasses.ComentInformation;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import retrofit.mime.TypedFile;

/**
 * Created by Alex on 30.03.2016.
 */
public class Retrofit {
    private static final String ENDPOINT = "http://testpb.alscon-clients.com";
    private static PostInterfaceRegistration postInterfaceRegistration;
    private static PostInterfaceSocialNetworks postInterfaceSocialNetworks;
    private static PostInterfaceLogin postInterfaceLogin;
    private static PostInterfaseAdvertisement postInterfaseAdvertisement;
    private static PostInterfaceNextPostAdvertisementFragment postInterfaceNextPostAdvertisementFragment;
    private static PostInterfaceDescriptionProblem postInterfaceDescriptionProblem;
    private static NewsJsonArray arrays;
    private static MyAdvertisementJsonArray myAdvertisementArrays;
    private static DetailNewsGetCommentInformation detailNewsGetCommentInformation;
    private static DetailNewsComentInformation detailNewsComentInformation;
    private static LikeNews likeNews;
    private static SelectedNews selectedNews;
    private static AdvertisementEdit advertisementEdit;
    private static AdvertisementEditFromServer advertisementEditFromServer;


    static {
        init();
    }

    private static void init() {
        RestAdapter postAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        postInterfaceRegistration = postAdapter.create(PostInterfaceRegistration.class);
        postInterfaceLogin = postAdapter.create(PostInterfaceLogin.class);
        postInterfaseAdvertisement = postAdapter.create(PostInterfaseAdvertisement.class);
        postInterfaceNextPostAdvertisementFragment = postAdapter.create(PostInterfaceNextPostAdvertisementFragment.class);
        postInterfaceSocialNetworks = postAdapter.create(PostInterfaceSocialNetworks.class);
        postInterfaceDescriptionProblem = postAdapter.create(PostInterfaceDescriptionProblem.class);
        arrays = postAdapter.create(NewsJsonArray.class);
        myAdvertisementArrays = postAdapter.create(MyAdvertisementJsonArray.class);
        detailNewsComentInformation = postAdapter.create(DetailNewsComentInformation.class);
        detailNewsGetCommentInformation = postAdapter.create(DetailNewsGetCommentInformation.class);
        advertisementEdit = postAdapter.create(AdvertisementEdit.class);
        advertisementEditFromServer = postAdapter.create(AdvertisementEditFromServer.class);
        likeNews = postAdapter.create(LikeNews.class);
        selectedNews = postAdapter.create(SelectedNews.class);
    }

    public static void sendSocialNetworks(Map<String, String> social, Callback<RegistrationResponseFromServer> callback) {
        postInterfaceSocialNetworks.sendSocialNetworks(social, callback);
    }

    public static void getCommentInformation(String newsId, Callback<List<ComentInformation>> callback) {
        detailNewsGetCommentInformation.getCommentInfo(newsId, callback);
    }

    public static void sendCommentInformation(Map<String, String> commentInformation, Callback<RegistrationResponseFromServer> callback) {
        detailNewsComentInformation.setCommentInfo(commentInformation, callback);
    }

    public static void sendEditInformationFromServer(Map<String, ?> edit, Callback<RegistrationResponseFromServer> callback) {
        advertisementEditFromServer.setEditData(edit, callback);
    }

    public static void sendAdvertisementEdit(String userId, String newsId, Callback<ResponseFromServerEditAdvertisement> callback) {
        advertisementEdit.setData(userId, newsId, callback);
    }

    public static void sendRegistrationData(Map<String, String> datas, TypedFile file, Callback<RegistrationResponseFromServer> callback) {
        postInterfaceRegistration.sendRegistrationData(datas, file, callback);
    }

    public static void sendLoginData(Map<String, String> loginDatas, Callback<RegistrationResponseFromServer> callback) {
        postInterfaceLogin.sendLoginData(loginDatas, callback);
    }

    public static void sendAdvertisementData(Map<String, String> advertisementDatas, Callback<RegistrationResponseFromServer> callback) {
        postInterfaseAdvertisement.sendAdvertisementData(advertisementDatas, callback);
    }

    public static void sendNextPostImage(Map<String, TypedFile> imageData, Callback<RegistrationResponseFromServer> callback) {
        postInterfaceNextPostAdvertisementFragment.sendNextPostImage(imageData, callback);
    }

    public static void sendAdvertisement(Map<String, String> dataAdvertisement, TypedFile file, Callback<RegistrationResponseFromServer> callback) {
        postInterfaceDescriptionProblem.sendAdvertisement(dataAdvertisement, file, callback);
    }

    public static void getArrays(String userId, Callback<RegistrationResponseFromServer> callback) {
        arrays.getArrays(userId, callback);
    }

    public static void getMyAdvertisementArrays(String userId, Callback<RegistrationResponseFromServer> callback) {
        myAdvertisementArrays.getMyAdvertisementArrays(userId, callback);
    }

    public static void getLike(String userId, String like, Callback<RegistrationResponseFromServer> callback) {
        likeNews.getLike(userId, like, callback);
    }

    public static void getSelectedNews(String userId, Callback<List<com.example.alex.helppeopletogether.SupportClasses.SelectedNews>> callback) {
        selectedNews.getSelectedNews(userId, callback);
    }

    interface MyAdvertisementJsonArray {
        @Multipart
        @POST("/user_advers.php")
        void getMyAdvertisementArrays(@Part("user_id") String userId, Callback<RegistrationResponseFromServer> callback);
    }

    interface DetailNewsGetCommentInformation {
        @Multipart
        @POST("/get_comment_by_adver_id.php")
        void getCommentInfo(@Part("adver_id") String newsId, Callback<List<ComentInformation>> callback);
    }

    interface DetailNewsComentInformation {
        @Multipart
        @POST("/add_comments.php")
        void setCommentInfo(@PartMap Map<String, String> commentInformation, Callback<RegistrationResponseFromServer> callback);
    }

    interface AdvertisementEdit {
        @POST("/user_adver.php")
        void setData(@Query("user_id") String userId, @Query("adver_id") String newsId, Callback<ResponseFromServerEditAdvertisement> callback);
    }

    interface AdvertisementEditFromServer {
        @Multipart
        @POST("/update_adver.php")
        void setEditData(@PartMap Map<String, ?> edit, Callback<RegistrationResponseFromServer> callback);
    }

    interface LikeNews {
        @Multipart
        @POST("/liked_advers.php")
        void getLike(@Part("user_id") String userId, @Part("adver_id") String like, Callback<RegistrationResponseFromServer> callback);
    }

    interface SelectedNews {
        @Multipart
        @POST("/user_liked_advers.php")
        void getSelectedNews(@Part("user_id") String userId, Callback<List<com.example.alex.helppeopletogether.SupportClasses.SelectedNews>> callback);
    }

    interface NewsJsonArray {
        //@Multipart
        @POST("/create_json.php")
        void getArrays(@Query("user_id") String userId, Callback<RegistrationResponseFromServer> callback);
    }

    interface PostInterfaceNextPostAdvertisementFragment {
        @Multipart
        @POST("/upload_files.php")
        void sendNextPostImage(@PartMap Map<String, TypedFile> imageData, Callback<RegistrationResponseFromServer> callback);
    }

    interface PostInterfaceSocialNetworks {
        @POST("/auth_social.php")
        void sendSocialNetworks(@QueryMap Map<String, String> social, Callback<RegistrationResponseFromServer> callback);
    }

    interface PostInterfaceDescriptionProblem {
        @Multipart
        @POST("/add_adver.php")
        void sendAdvertisement(@QueryMap Map<String, String> dataAdvertisement, @Part("imageAdvertisement") TypedFile file, Callback<RegistrationResponseFromServer> callback);

    }

    interface PostInterfaceRegistration {
        @Multipart
        @POST("/registration.php")
        void sendRegistrationData(@QueryMap Map<String, String> datas, @Part("imageFile") TypedFile file, Callback<RegistrationResponseFromServer> callback);
    }

    interface PostInterfaceLogin {

        @POST("/login.php")
        void sendLoginData(@QueryMap Map<String, String> loginDatas, Callback<RegistrationResponseFromServer> callback);
    }

    interface PostInterfaseAdvertisement {
        @POST("/add_user_information.php")
        void sendAdvertisementData(@QueryMap Map<String, String> advertisementDatas, Callback<RegistrationResponseFromServer> callback);
    }
}
