/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.swtproject;

/**
 *
 * @author Michael
 */
public class User {

    private String name;
    private String userId;
    private String[] likedMovieIds;

    User() {

    }

    ;
    public User(String name, String userId, String[] likedMovieIds) {
        this.name = name;
        this.userId = userId;
        this.likedMovieIds = likedMovieIds;

    }
    // sort liked movieIds

    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setLikedMovieIds(String[] likedMovieIds) {
        this.likedMovieIds = likedMovieIds;
    }

    public String[] getLikedMovieIds() {
        return likedMovieIds;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

}
