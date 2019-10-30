package com.udacity.course3.reviews.entity;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Mongo document, contains necessary bits of products(the ID for lookups), reviews, and comments
 */
@Document("reviews")
public class MongoReview {
    @Id
    @Field("_id")
    private String Id;
    @Field("review_id")
    private Integer reviewId;
    @Field("product_id")
    private Integer productId;
    private Date created_at;
    @Field("review_author")
    private String reviewAuthor;
    @Field("review_text")
    private String reviewText;
    @Field("comments")
    private ArrayList<MongoComment> comments;

    public Integer getReviewId() {
        return this.reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public Integer getProductId() {
        return this.productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Date getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getReviewAuthor() {
        return this.reviewAuthor;
    }

    public void setReviewAuthor(String reviewAuthor) {
        this.reviewAuthor = reviewAuthor;
    }

    public String getReviewText() {
        return this.reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public ArrayList<MongoComment> getComments() {
        return this.comments;
    }

    public void setComments(ArrayList<MongoComment> comments) {
        this.comments = comments;
    }
}