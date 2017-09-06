package com.meng.newsreader.presentation.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mengzhou on 7/3/17.
 */

public class Article implements Serializable{

    public String _id;
    public String snippet;
    public String summary;
    public List<Media> multimedia;
    public String pub_date;
//    public Author byline;
    public String lead_paragraph;
    public int word_count;
    public String source;
    public String web_url;

    public Article(){}

    public Article(String snippet, String lead_paragraph) {
        this.snippet = snippet;
        this.lead_paragraph = lead_paragraph;
    }

    public class Media {
        public int width;
        public int height;
        public String url;
        public int rank;
        public String subtype;
        public String type;
    }

    public class Author {
        public String original;
        public List<Person> person;
    }

    public class Person {
        public String organization;
        public String role;
        public int rank;
        public String firstname;
        public String lastname;
    }

}
