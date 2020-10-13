package com.example.prac;

public class musicfiles {


    private String path;
    private String title;
    private String album;
    private String duration;
    private String artist;

    public musicfiles(String path, String title, String album, String duration, String artist) {
        this.path = path;
        this.title = title;
        this.album = album;
        this.duration = duration;
        this.artist = artist;
    }

    public musicfiles() {

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }


}
