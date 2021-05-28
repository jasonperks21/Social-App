package com.cs334.project3.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(
            strategy= GenerationType.AUTO,
            generator="native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private Long category_id;

    @Column
    private String categoryName;

    @Column
    private String description;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    public Category(String name, String description) {
        this.categoryName = name;
        this.description = description;
        posts = new ArrayList<>();
    }

    /**
     * Add a post to this category.
     * @param post The post.
     */
    protected void addPost(Post post){
        posts.add(post);
    }
}
