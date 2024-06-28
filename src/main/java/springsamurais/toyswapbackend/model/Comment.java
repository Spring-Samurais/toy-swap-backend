package springsamurais.toyswapbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Member commenter;

    @ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "", nullable = false)
    private Listing listing;

    private Date dateCommented;

}
