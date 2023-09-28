package com.belyaeva.places.domain;

public class Comment {

    private Long id;

    private String content;

    private String nameAuthor;

    public Comment(Long id, String content, String nameAuthor) {
        this.id = id;
        this.content = content;
        this.nameAuthor = nameAuthor;
    }

    public Comment(String content, String nameAuthor) {
        this.content = content;
        this.nameAuthor = nameAuthor;
    }

    public Long getId() { return id; }
    public String getContent() { return content; }
    public String getNameAuthor() { return nameAuthor; }
}
