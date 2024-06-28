package springsamurais.toyswapbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.expression.spel.ast.NullLiteral;
import springsamurais.toyswapbackend.exception.ListingNotFoundException;
import springsamurais.toyswapbackend.model.*;
import springsamurais.toyswapbackend.repository.ListingRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DataJpaTest
class ListingServiceImplementationTest {

    @Mock
    private ListingRepository listingRepository;

    @InjectMocks
    private ListingServiceImplementation serviceImplementation;

    private Member memberOne;
    private List<Listing> listings;

    @BeforeEach
    void setUp() {
        memberOne = new Member(1L, "Test Member", "member", "location", null);

        Listing listing = new Listing(1L, "A listing test", null, memberOne, null, Category.ACTION_FIGURES, "I am a description :-)", ItemCondition.GOOD, Status.AVAILABLE, null);
        Listing listing2 = new Listing(2L, "Another listing test", null, memberOne, null, Category.DOLLS, "Description2", ItemCondition.LIKE_NEW, Status.PENDING, null);
        Listing listing3 = new Listing(3L, "Third listing test", null, memberOne, null, Category.CONSTRUCTION_TOYS, "Description3", ItemCondition.LIKE_NEW, Status.SWAPPED, null);
        Listing listing4 = new Listing(4L, "Fourth listing test", null, memberOne, null, Category.VEHICLES, "Description4", ItemCondition.USED, Status.AVAILABLE, null);
        Listing listing5 = new Listing(5L, "Fifth listing test", null, memberOne, null, Category.EDUCATIONAL_TOYS, "Description5", ItemCondition.DAMAGED, Status.PENDING, null);
        listings = Arrays.asList(listing, listing2, listing3, listing4, listing5);
    }

    @Test
    void getAllListings() {
        when(listingRepository.findAll()).thenReturn(listings);

        List<Listing> result = serviceImplementation.getAllListings();

        assertEquals(5, result.size());
        assertEquals("A listing test", result.getFirst().getTitle());
        assertEquals("I am a description :-)", result.getFirst().getDescription());
        assertEquals("Another listing test", result.get(1).getTitle());
    }


    @Test
    void getListingById() {
        when(listingRepository.findById(1L)).thenReturn(Optional.ofNullable(listings.getFirst()));

        assertEquals("A listing test", serviceImplementation.getListingById(1L).getTitle());
        assertEquals("I am a description :-)", serviceImplementation.getListingById(1L).getDescription());
        assertEquals(ItemCondition.GOOD, serviceImplementation.getListingById(1L).getCondition());
    }

    @Test
    @DisplayName("Throws ListingNotFoundException.class when no ID is found ")
    void testGetListingByIdWhenListingDoesNotExistThenThrowException() {
        when(listingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ListingNotFoundException.class, () -> serviceImplementation.getListingById(1L));
    }

    @Test
    @DisplayName("Test saveListing method when a valid listing is passed")
    void testSaveListingWhenValidListingThenReturnListing() {
        Listing listing = new Listing(6L, "New listing test", null, memberOne, null, Category.EDUCATIONAL_TOYS, "New description", ItemCondition.BRAND_NEW, Status.AVAILABLE, null);
        when(listingRepository.save(any(Listing.class))).thenReturn(listing);

        Listing result = serviceImplementation.saveListing(listing);

        assertNotNull(result);
        assertEquals("New listing test", result.getTitle());
        assertEquals("New description", result.getDescription());
        assertEquals(ItemCondition.BRAND_NEW, result.getCondition());
    }



}