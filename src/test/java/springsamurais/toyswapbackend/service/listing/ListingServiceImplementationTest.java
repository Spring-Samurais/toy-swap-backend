package springsamurais.toyswapbackend.service.listing;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import springsamurais.toyswapbackend.exception.*;
import springsamurais.toyswapbackend.model.*;
import springsamurais.toyswapbackend.repository.ListingRepository;
import springsamurais.toyswapbackend.repository.MemberRepository;
import springsamurais.toyswapbackend.service.imgurapi.service.ImgurService;
import springsamurais.toyswapbackend.service.member.MemberServiceImplementation;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@DataJpaTest
class ListingServiceImplementationTest {

    @Mock
    private ListingRepository listingRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private MemberServiceImplementation memberServiceImplementation;
    @Mock
    private ImgurService imgurService;

    @InjectMocks
    private ListingServiceImplementation serviceImplementation;

    private Member memberOne;
    private List<Listing> listings;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        memberOne = new Member(1L, "Test Member", "member", "location", null);

        Listing listing = new Listing(1L, "A listing test", LocalDateTime.now(), Category.ACTION_FIGURES, "I am a description :-)", ItemCondition.GOOD, Status.AVAILABLE, memberOne, null, null);
        Listing listing2 = new Listing(2L, "Another listing test", LocalDateTime.now(), Category.DOLLS, "Description2", ItemCondition.LIKE_NEW, Status.PENDING, memberOne, null, null);
        Listing listing3 = new Listing(3L, "Third listing test", LocalDateTime.now(), Category.CONSTRUCTION_TOYS, "Description3", ItemCondition.LIKE_NEW, Status.SWAPPED, memberOne, null, null);
        Listing listing4 = new Listing(4L, "Fourth listing test", LocalDateTime.now(), Category.VEHICLES, "Description4", ItemCondition.USED, Status.AVAILABLE, memberOne, null, null);
        Listing listing5 = new Listing(5L, "Fifth listing test", LocalDateTime.now(), Category.EDUCATIONAL_TOYS, "Description5", ItemCondition.DAMAGED, Status.PENDING, memberOne, null, null);
        listings = Arrays.asList(listing, listing2, listing3, listing4, listing5);
    }

    @Test
    void getAllListings() {
        when(listingRepository.findAll()).thenReturn(listings);

        List<Listing> result = serviceImplementation.getAllListings();

        assertEquals(5, result.size());
        assertEquals("A listing test", result.get(0).getTitle());
        assertEquals("I am a description :-)", result.get(0).getDescription());
        assertEquals("Another listing test", result.get(1).getTitle());
    }

    @Test
    void getListingById() {
        when(listingRepository.findById(1L)).thenReturn(Optional.ofNullable(listings.get(0)));

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
    void testSaveListingWhenValidListingThenReturnListing() throws MemberNotFoundException, IOException {
        ListingDTO listingDTO = new ListingDTO();
        listingDTO.setTitle("New listing test");
        listingDTO.setMemberId(1L);
        listingDTO.setCategory("CONSTRUCTION_TOYS");
        listingDTO.setDescription("New description");
        listingDTO.setCondition("USED");
        listingDTO.setStatusListing("AVAILABLE");

        MockMultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "image content".getBytes());
        listingDTO.setImageFiles(Collections.singletonList(image));

        Member member = new Member(1L, "Test Member", "member", "location", null);
        Listing expectedListing = new Listing(6L, "New listing test", LocalDateTime.now(), Category.CONSTRUCTION_TOYS, "New description", ItemCondition.USED, Status.AVAILABLE, member, null, null);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(imgurService.uploadImage(anyString())).thenReturn("http://imgur.com/image.jpg");
        when(listingRepository.save(any(Listing.class))).thenReturn(expectedListing);

        Listing result = serviceImplementation.saveListing(listingDTO);

        assertNotNull(result);
        assertEquals("New listing test", result.getTitle());
        assertEquals("New description", result.getDescription());
        assertEquals(ItemCondition.USED, result.getCondition());
        assertEquals(Status.AVAILABLE, result.getStatusListing());
        assertEquals(member, result.getMember());
    }

    @Test
    void updateListingTest() throws MemberNotFoundException, IOException {
        Listing listingOne = listings.get(0);
        Listing listingUpdate = new Listing(1L, "New listing test", LocalDateTime.now(), Category.CONSTRUCTION_TOYS, "New description", ItemCondition.GOOD, Status.AVAILABLE, memberOne, null, null);

        Mockito.when(listingRepository.existsById(1L)).thenReturn(true);
        Mockito.when(listingRepository.save(listingUpdate)).thenReturn(listingUpdate);

        Listing updatedListing = serviceImplementation.updateListing(listingUpdate);
        assertEquals("New listing test", updatedListing.getTitle());
        assertEquals("New description", updatedListing.getDescription());
    }

    @Test
    void updateNotFoundListingTest() throws MemberNotFoundException, IOException {
        when(listingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ListingNotFoundException.class, () -> serviceImplementation.updateListing(listings.get(0)));

        verify(listingRepository).existsById(1L);
    }

    @Test
    void testDeleteListingById_Success() {
        when(listingRepository.findById(listings.get(0).getId())).thenReturn(Optional.of(listings.get(0)));

        serviceImplementation.deleteListingById(listings.get(0).getId());

        verify(listingRepository, times(1)).findById(listings.get(0).getId());
        verify(listingRepository, times(1)).delete(listings.get(0));
    }

    @Test
    @DisplayName("Delete but given ID can't be found")
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
        Long memberId = 6L;
        when(listingRepository.findByMemberId(memberId)).thenReturn(Collections.emptyList());

        assertThrows(MemberNotFoundException.class, () -> { serviceImplementation.deleteListingsByMember(memberId);});

        verify(listingRepository, times(1)).findByMemberId(memberId);
        verify(listingRepository, times(0)).deleteAll(anyList());
    }
}