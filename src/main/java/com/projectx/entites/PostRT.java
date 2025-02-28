package com.projectx.entites;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "tb_postrt")
public class PostRT implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post postRT;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User userRT;

    public PostRT(Post post, User user) {
        this.postRT = post;
        this.userRT = user;
    }
}
