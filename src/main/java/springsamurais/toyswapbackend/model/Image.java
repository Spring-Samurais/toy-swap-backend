package springsamurais.toyswapbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Entity
@Table(name = "image")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageName;

    @ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "", nullable = false)
    private Listing listing;

    @Lob
    @Column(nullable = false)
    private Blob image;

}
