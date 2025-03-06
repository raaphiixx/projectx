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
    @JoinColumn(name = "followingId")
    private User following;

    @ManyToOne
    @JoinColumn(name = "followedId")
    private User followed;

    public UserFollow(User following, User followed) {
        this.following = following;
        this.followed = followed;
    }
}
