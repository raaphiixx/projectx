package com.projectx.entites;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "tb_posts")
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String content;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User owner;

    @OneToMany(mappedBy = "post")
    private Set<PostLike> likes = new HashSet<>();


    public Post(String content, User owner) {
        this.content = content;
        this.owner = owner;
    }
}
