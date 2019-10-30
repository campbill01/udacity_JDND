package com.udacity.course3.reviews.entity;

import java.util.Date;

/**
 * Object to save nested comment in Mongo document
 */
public class MongoComment{
    private String commentAuthor;
    private String commentText;
    private Date created_at;

    public String getCommentAuthor() {
        return this.commentAuthor;
    }

    public void setCommentAuthor(String commentAuthor) {
        this.commentAuthor = commentAuthor;
    }

    public String getCommentText() {
        return this.commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Date getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

}