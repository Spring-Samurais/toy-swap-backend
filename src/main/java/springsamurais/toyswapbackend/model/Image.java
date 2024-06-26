package springsamurais.toyswapbackend.model;

import jakarta.persistence.*;

import java.sql.Blob;

@Entity
//@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageName;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "", nullable = false)
    private Post post;

    @Lob
    @Column(nullable = false)
    private Blob image;

    public Image() {
    }

    public Image(String imageName, Post post, Blob image) {
        this.imageName = imageName;
        this.post = post;
        this.image = image;
    }
}
