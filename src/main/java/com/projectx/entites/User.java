package com.projectx.entites;

import com.projectx.entites.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "tb_users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String login;
    private String password;
    private String name;
    private String lname;
    private String email;
    private UserRole role;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "tb_users_posts",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "postId")
    )
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "following")
    private Set<UserFollow> following = new HashSet<>();

    @OneToMany(mappedBy = "followed")
    private Set<UserFollow> followed = new HashSet<>();

    public User(String login, String password, String name, String lname, String email) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.lname = lname;
        this.email = email;
        this.role = UserRole.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return "";
    }
}
