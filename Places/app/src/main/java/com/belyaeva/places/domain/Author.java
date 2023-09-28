package com.belyaeva.places.domain;

import java.util.List;

public class Author {

    private Long id;

    private String name;

    private String pass;

    private List<Publication> publicationList;

    public Author(Long id, String name, String pass, List<Publication> publicationList) {
        this.id = id;
        this.name = name;
        this.pass = pass;
        this.publicationList = publicationList;
    }

    public Author(String name, String pass, List<Publication> publicationList) {
        this.name = name;
        this.pass = pass;
        this.publicationList = publicationList;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getPass() { return pass; }
    public List<Publication> getPublicationList() { return publicationList; }
}
