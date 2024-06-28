package springsamurais.toyswapbackend.service;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.expression.spel.ast.NullLiteral;
import springsamurais.toyswapbackend.exception.*;
import springsamurais.toyswapbackend.model.*;
import springsamurais.toyswapbackend.repository.ListingRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void testDeleteListingById_Success() throws ListingNotFoundException {
        when(listingRepository.findById(listings.get(0).getId())).thenReturn(Optional.of(listings.get(0)));

        serviceImplementation.deleteListingById(listings.get(0).getId());

        verify(listingRepository, times(1)).findById(listings.get(0).getId());
        verify(listingRepository, times(1)).delete(listings.get(0));
    }

    @Test
    void testDeleteListingById_NotFound() {
        when(listingRepository.findById(listings.get(0).getId())).thenReturn(Optional.empty());

        assertThrows(ListingNotFoundException.class, () -> {
            serviceImplementation.deleteListingById(listings.get(0).getId());
        });

        verify(listingRepository, times(1)).findById(listings.get(0).getId());
        verify(listingRepository, times(0)).delete(any(Listing.class));
    }

    @Test
    void testDeleteListingsByMember_Success() throws ListingNotFoundException, MemberNotFoundException {
        Long memberId = memberOne.getId();
        when(listingRepository.findByMemberId(memberId)).thenReturn(listings);

        serviceImplementation.deleteListingsByMember(memberId);

        verify(listingRepository, times(1)).findByMemberId(memberId);
        verify(listingRepository, times(1)).deleteAll(listings);
    }

    @Test
    void testDeleteListingsByMember_MemberNotFound() {
        Long memberId = memberOne.getId();
        when(listingRepository.findByMemberId(memberId)).thenReturn(Collections.emptyList());

        assertThrows(MemberNotFoundException.class, () -> {
            serviceImplementation.deleteListingsByMember(memberId);
        });

        verify(listingRepository, times(1)).findByMemberId(memberId);
        verify(listingRepository, times(0)).deleteAll(anyList());
    }
}