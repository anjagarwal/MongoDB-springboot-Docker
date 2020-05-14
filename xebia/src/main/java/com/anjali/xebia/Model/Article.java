package com.anjali.xebia.Model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Document(collection = "Article")
public class Article {
    @Id
    String id;
    @NonNull
    String title;
    @NonNull
    String description;
    @NonNull
    String body;
    List<String> tags;
    String slug;
    Date createdAt;
    Date updatedAt;
    boolean favourited;
    int favouritesCount;

    public Article(@NonNull String title, @NonNull String description, @NonNull String body, List<String> tags) {
        this.title = title;
        this.description = description;
        this.body = body;
        this.tags = tags;
    }
}
