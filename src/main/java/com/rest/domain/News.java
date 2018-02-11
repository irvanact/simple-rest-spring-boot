package com.rest.domain;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "news_id_seq")
    @SequenceGenerator(name = "news_id_seq", sequenceName = "news_id_seq", allocationSize=1)
    @Column(name="news_id")
    long newsId;

    String content;

    String title;

    String status;

    @Column(columnDefinition= "TIME WITH TIME ZONE")
    Date createAt;

    @Column(columnDefinition= "TIME WITH TIME ZONE")
    Date updateAt;

    @ManyToMany
    @JoinTable(name = "news_topic_mapping", joinColumns = @JoinColumn(name = "news_id", referencedColumnName = "news_id"), inverseJoinColumns = @JoinColumn(name = "topic_id", referencedColumnName = "topic_id"))
    @JsonManagedReference(value = "topics")
    List<Topic> topics;

    public News(long newsId, String content, String title, String status, Date createAt, Date updateAt) {
        this.newsId = newsId;
        this.content = content;
        this.title = title;
        this.status = status;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public News(){

    }

    public long getNewsId() {
        return newsId;
    }

    public void setNewsId(long newsId) {
        this.newsId = newsId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }
}
