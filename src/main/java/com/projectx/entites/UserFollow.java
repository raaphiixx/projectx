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
@Table(name = "tb_usefollow")
public class UserFollow implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "followerId")
    private User follower;

    @ManyToOne
    @JoinColumn(name = "followedId")
    private User followed;

    public UserFollow(User follower, User followed) {
        this.follower = follower;
        this.followed = followed;
    }
}
