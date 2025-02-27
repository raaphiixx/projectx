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
@Table(name = "tb_users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String login;
    private String password;
    private String name;
    private String lname;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "tb_users_posts",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "postId")
    )
    private Set<Post> posts = new HashSet<>();

    public User(String login, String password, String name, String lname) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.lname = lname;
    }
}
