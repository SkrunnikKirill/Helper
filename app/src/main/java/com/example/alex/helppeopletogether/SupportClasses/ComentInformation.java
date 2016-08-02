package com.example.alex.helppeopletogether.SupportClasses;

/**
 * Created by Alex on 26.07.2016.
 */
public class ComentInformation {
    public String full_name;
    public String avatar;
    public String comment;
    public String comment_id;
    public String created_at;


    public ComentInformation(String full_name, String avatar, String comment, String created_at) {
        this.full_name = full_name;
        this.avatar = avatar;
        this.comment = comment;
        this.created_at = created_at;

    }
}
