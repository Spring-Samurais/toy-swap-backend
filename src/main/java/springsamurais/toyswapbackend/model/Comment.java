package springsamurais.toyswapbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import java.util.Date;

@Entity
@Table(name = "comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "", nullable = false)
    @JsonIgnoreProperties({"listings"})
    private Member commenter;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Listing listing;

    private LocalDateTime dateCommented;

}
