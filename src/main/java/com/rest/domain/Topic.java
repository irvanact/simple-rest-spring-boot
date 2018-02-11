package com.rest.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@JsonIgnoreProperties(value = {"createAt", "updateAt"},
        allowGetters = true, ignoreUnknown = true)
public class Topic  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topic_id_seq")
    @SequenceGenerator(name = "topic_id_seq", sequenceName = "topic_id_seq", allocationSize=1)
    @Column(name="topic_id")
    long topicId;

    String topicName;

    String description;

    @Column(columnDefinition= "TIME WITH TIME ZONE")
    Date createAt;

    @Column(columnDefinition= "TIME WITH TIME ZONE")
    Date updateAt;

    @ManyToMany
    @JoinTable(name = "news_topic_mapping", joinColumns = @JoinColumn(name = "topic_id", referencedColumnName = "topic_id"), inverseJoinColumns = @JoinColumn(name = "news_id", referencedColumnName = "news_id"))
    @JsonBackReference(value = "news")
    List<News> news;

    public Topic(long topicId, String topicName, String description, Date createAt, Date updateAt) {
        this.topicId = topicId;
        this.topicName = topicName;
        this.description = description;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public Topic(){

    }

    public long getTopicId() {
        return topicId;
    }

    public void setTopicId(long topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }
}
