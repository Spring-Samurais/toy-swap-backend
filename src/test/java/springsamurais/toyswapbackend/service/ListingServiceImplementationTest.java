package springsamurais.toyswapbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.expression.spel.ast.NullLiteral;
import springsamurais.toyswapbackend.model.*;
import springsamurais.toyswapbackend.repository.ListingRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
class ListingServiceImplementationTest {

    @Mock
    private ListingRepository listingRepository;

    @InjectMocks
    private ListingServiceImplementation serviceImplementation;

    private Member memberOne;


    @BeforeEach
    void setUp() {
       memberOne = new Member(1L,"Test Member","member","location",null);
    }

    @Test
    void getAllListings() {
        Listing listing = new Listing(1L,"A listing test", null, memberOne,null,
                Category.ACTION_FIGURES,"I am a description :-)", ItemCondition.GOOD, Status.AVAILABLE,null);
        Listing listing2 = new Listing(2L, "Another listing test", null, memberOne, null,
                Category.DOLLS, "Description2", ItemCondition.LIKE_NEW, Status.PENDING, null);

        List<Listing> listings = Arrays.asList(listing, listing2);


        when(listingRepository.findAll()).thenReturn(listings);

        List<Listing> result = serviceImplementation.getAllListings();

        assertEquals(2,result.size());
        assertEquals("A listing test",result.getFirst().getTitle());
        assertEquals("I am a description :-",result.getFirst().getDescription());
        assertEquals("Another listing test",result.getLast().getTitle());
    }



}