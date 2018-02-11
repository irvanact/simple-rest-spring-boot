package com.rest.domain;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Entity
public class NewsTopicMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "news_topic_id_seq")
    @SequenceGenerator(name = "news_topic_id_seq", sequenceName = "news_topic_id_seq", allocationSize=1)
    @Column(name="id")
    long id;


    @ManyToOne
    @JoinColumn(name = "news_id")
    @NotFound(action = NotFoundAction.IGNORE)
    News news;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    @NotFound(action = NotFoundAction.IGNORE)
    Topic topic;

    public NewsTopicMapping(){

    }

    public NewsTopicMapping( News news, Topic topic) {
        this.news = news;
        this.topic = topic;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
