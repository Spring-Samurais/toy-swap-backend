package springsamurais.toyswapbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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

    private String password;

    private String location;

    @Column
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties({"photo", "datePosted", "category", "description", "condition", "statusListing", "comments", "member"})
    //@JoinColumn(name = "", nullable = false)
    @JsonManagedReference
    private List<Listing> listings;
}
