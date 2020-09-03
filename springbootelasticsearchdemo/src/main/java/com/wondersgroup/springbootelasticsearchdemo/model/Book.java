package com.wondersgroup.springbootelasticsearchdemo.model;

import org.springframework.data.elasticsearch.annotations.Document;

//存储的索引名字为estestbook，类型为book
//查询全部：http://192.168.205.150:9200/estestbook/_search
//查询地址：http://192.168.205.150:9200/estestbook/com.wondersgroup.springbootelasticsearchdemo.model.Book/1/
@Document(indexName = "estestbook",type = "book")   //这个type设置貌似不起作用了，所以/estestbook/book这个路径不行
public class Book {
    private Integer id;
    private String bookName;
    private String author;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
