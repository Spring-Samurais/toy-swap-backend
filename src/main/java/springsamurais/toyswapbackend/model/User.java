package springsamurais.toyswapbackend.model;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @Column(unique = true)
    @NotNull
    private String nickname;


    private String location;

    @Column
    @OneToMany(fetch = FetchType.LAZY)
    //@JoinColumn(name = "", nullable = false)
    private List<Listing> listings;

    // Constructors
    public User() {
    }

    public User(String name, String nickname, String location, List<Listing> listings) {
        this.name = name;
        this.nickname = nickname;
        this.location = location;
        this.listings = new ArrayList<>();

    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Listing> getPosts() {
        return listings;
    }

    public void setPosts(List<Listing> listings) {
        this.listings = listings;
    }

}
