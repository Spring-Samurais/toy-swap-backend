package springsamurais.toyswapbackend.service;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.mockito.MockitoAnnotations;
import springsamurais.toyswapbackend.exception.*;
import springsamurais.toyswapbackend.model.*;
import springsamurais.toyswapbackend.repository.ListingRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListingServiceTest {

    @Mock
    private ListingRepository listingRepository;

    @InjectMocks
    private ListingServiceImplementation listingService;

    private Listing listing;

    private Member memberOne;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        memberOne = new Member(1L, "Test Member", "member", "location", null);

        listing = new Listing(1L, "A listing test", null, memberOne, null, Category.ACTION_FIGURES, "I am a description :-)", ItemCondition.GOOD, Status.AVAILABLE, null);
//        Listing listing2 = new Listing(2L, "Another listing test", null, memberOne, null, Category.DOLLS, "Description2", ItemCondition.LIKE_NEW, Status.PENDING, null);
//        Listing listing3 = new Listing(3L, "Third listing test", null, memberOne, null, Category.CONSTRUCTION_TOYS, "Description3", ItemCondition.LIKE_NEW, Status.SWAPPED, null);
//        Listing listing4 = new Listing(4L, "Fourth listing test", null, memberOne, null, Category.VEHICLES, "Description4", ItemCondition.USED, Status.AVAILABLE, null);
//        Listing listing5 = new Listing(5L, "Fifth listing test", null, memberOne, null, Category.EDUCATIONAL_TOYS, "Description5", ItemCondition.DAMAGED, Status.PENDING, null);


    }

    @Test
    void testDeleteListingById_Success() throws ListingNotFoundException {
        when(listingRepository.findById(listing.getId())).thenReturn(Optional.of(listing));

        listingService.deleteListingById(listing.getId());

        verify(listingRepository, times(1)).findById(listing.getId());
        verify(listingRepository, times(1)).delete(listing);
    }

    @Test
    void testDeleteListingById_NotFound() {
        when(listingRepository.findById(listing.getId())).thenReturn(Optional.empty());

        assertThrows(ListingNotFoundException.class, () -> {
            listingService.deleteListingById(listing.getId());
        });

        verify(listingRepository, times(1)).findById(listing.getId());
        verify(listingRepository, times(0)).delete(listing);
    }

    @Test
    void testDeleteListingsByMember_Success() throws ListingNotFoundException, MemberNotFoundException {
        Long memberId = 1L;
        List<Listing> listings = Collections.singletonList(listing);
        when(listingRepository.findByMemberId(memberId)).thenReturn(listings);

        listingService.deleteListingsByMember(memberId);

        verify(listingRepository, times(1)).findByMemberId(memberId);
        verify(listingRepository, times(1)).deleteAll(listings);
    }

    @Test
    void testDeleteListingsByMember_MemberNotFound() {
        Long memberId = 1L;
        when(listingRepository.findByMemberId(memberId)).thenReturn(Collections.emptyList());

        assertThrows(MemberNotFoundException.class, () -> {
            listingService.deleteListingsByMember(memberId);
        });

        verify(listingRepository, times(1)).findByMemberId(memberId);
        verify(listingRepository, times(0)).deleteAll(anyList());
    }
}