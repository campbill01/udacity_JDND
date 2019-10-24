package com.udacity.course3.reviews.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
/**
 * Comments belong to reviews
 * 
 */
@Entity
@Table(name ="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="comment_id")
    private Integer commentId;

    @ManyToOne(targetEntity = Review.class)
    @JoinColumn(name="review_id", referencedColumnName = "review_id")
    private Review review;

    public Review getReview() {
        return this.review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    @Column(name="comment_text")
    private String commentText;

    @Column(name="comment_author")
    private String commentAuthor;

    @Column(name="created_at")
    @CreationTimestamp
    private Timestamp commentTime;

    public String getCommentText() {
        return this.commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getCommentAuthor() {
        return this.commentAuthor;
    }

    public void setCommentAuthor(String commentAuthor) {
        this.commentAuthor = commentAuthor;
    }

    public Timestamp getCommentTime() {
        return this.commentTime;
    }

    public void setCommentTime(Timestamp commentTime) {
        this.commentTime = commentTime;
    }

}