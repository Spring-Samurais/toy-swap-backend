package springsamurais.toyswapbackend.model;

import jakarta.persistence.*;

import java.sql.Blob;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageName;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "", nullable = false)
    private Listing listing;

    @Lob
    @Column(nullable = false)
    private Blob image;

    public Image() {
    }

    public Image(String imageName, Listing listing, Blob image) {
        this.imageName = imageName;
        this.listing = listing;
        this.image = image;
    }
}
