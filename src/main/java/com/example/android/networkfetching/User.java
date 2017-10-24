package com.example.android.networkfetching;

/**
 * Created by me_singh on 2/10/17.
 */

public class User {

    String login,id,avatar,url,score;

    public User(String login, String id, String avatar, String url, String score) {
        this.login = login;
        this.id = id;
        this.avatar = avatar;
        this.url = url;
        this.score = score;
    }

    public String getLogin() {
        return login;
    }

    public String getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getUrl() {
        return url;
    }

    public String getScore() {
        return score;
    }
}
