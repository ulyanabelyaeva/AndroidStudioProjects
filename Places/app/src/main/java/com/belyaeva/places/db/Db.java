package com.belyaeva.places.db;

import com.belyaeva.places.domain.Author;
import com.belyaeva.places.domain.Comment;
import com.belyaeva.places.domain.Publication;

import java.util.ArrayList;
import java.util.List;

public class Db {

    private Db(){}

    public static final List<Author> AUTHOR_LIST = new ArrayList<>();
    public static final List<Publication> PUBLICATION_LIST = new ArrayList<>();
    public static final List<Comment> COMMENT_LIST_BY_BOOK = new ArrayList<>();
}
