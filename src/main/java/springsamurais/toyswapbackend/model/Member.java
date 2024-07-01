package springsamurais.toyswapbackend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;



import java.util.List;

@Entity
@Table(name = "member")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {

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
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonManagedReference
    //@JoinColumn(name = "", nullable = false)
    private List<Listing> listings;

}
