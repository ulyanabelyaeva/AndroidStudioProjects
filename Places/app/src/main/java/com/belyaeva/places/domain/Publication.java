package com.belyaeva.places.domain;

import android.graphics.Bitmap;

import java.io.File;
import java.util.List;

public class Publication {

    private Long id;

    private Bitmap image;

    private String icon;

    private String info;

    private String nameAuthor;

    private List<Comment> commentList;

    public Publication(Long id, Bitmap image, String icon, String info, String nameAuthor, List<Comment> commentList) {
        this.id = id;
        this.image = image;
        this.icon = icon;
        this.info = info;
        this.nameAuthor = nameAuthor;
        this.commentList = commentList;
    }

    public Publication(Long id, Bitmap image, String info, String nameAuthor, List<Comment> commentList) {
        this.id = id;
        this.image = image;
        this.info = info;
        this.nameAuthor = nameAuthor;
        this.commentList = commentList;
    }

    public Publication(Long id, String icon, String info, String nameAuthor, List<Comment> commentList) {
        this.id = id;
        this.icon = icon;
        this.info = info;
        this.nameAuthor = nameAuthor;
        this.commentList = commentList;
    }

    public Publication(String icon, String info, String nameAuthor, List<Comment> commentList) {
        this.icon = icon;
        this.info = info;
        this.nameAuthor = nameAuthor;
        this.commentList = commentList;
    }

    public Long getId() { return id; }
    public Bitmap getImage() { return image; }
    public String getInfo() { return info; }
    public String getNameAuthor() { return nameAuthor; }
    public String getIcon() { return icon; }
    public List<Comment> getCommentList() { return commentList; }
}
