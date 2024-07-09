package springsamurais.toyswapbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import springsamurais.toyswapbackend.exception.ListingNotFoundException;
import springsamurais.toyswapbackend.model.*;
import springsamurais.toyswapbackend.service.listing.ListingServiceImplementation;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@SpringBootTest
class ListingControllerTest {

    @Mock
    private ListingServiceImplementation mockListingService;

    @InjectMocks
    private ListingController listingController;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper;
    private Member memberOne;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(listingController).build();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        memberOne = new Member(1L, "Test Member", "member", "location","password","something", null);
    }


    @Test
    @DisplayName("GET -> All Listings")
    void getAllItemsTest() throws Exception {
        Listing listing = new Listing(1L, "A listing test", LocalDateTime.now(), Category.ACTION_FIGURES, "description", ItemCondition.GOOD, Status.AVAILABLE, memberOne, null, null);
        Listing listing2 = new Listing(2L, "Another listing test", LocalDateTime.now(), Category.DOLLS, "description", ItemCondition.BRAND_NEW, Status.AVAILABLE, memberOne, null, null);

        List<Listing> listings = Arrays.asList(listing, listing2);

        when(mockListingService.getAllListings()).thenReturn(listings);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/listings"))
                .andExpectAll(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(listings)));
    }

    @Test
    @DisplayName("GET -> ByID")
    void getByIdTest() throws Exception {
        Listing listing = new Listing(1L, "A listing test", LocalDateTime.now(), Category.ACTION_FIGURES, "description", ItemCondition.GOOD, Status.AVAILABLE, memberOne, null, null);
        when(mockListingService.getListingById(1L)).thenReturn(listing);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/listings/1"))
                .andExpectAll(MockMvcResultMatchers.status().isOk())
                .andExpectAll(MockMvcResultMatchers.content().json(mapper.writeValueAsString(listing)));
    }

    @Test
    @DisplayName("POST -> Save Listing")
    void saveListingTest() throws Exception {
        ListingDTO newListingDTO = new ListingDTO();
        newListingDTO.setTitle("New listing test");
        newListingDTO.setMemberId(1L);
        newListingDTO.setCategory("CONSTRUCTION_TOYS");
        newListingDTO.setDescription("New description");
        newListingDTO.setCondition("USED");
        newListingDTO.setStatusListing("AVAILABLE");

        Listing expectedListing = new Listing(
                1L, // id
                "New listing test", // title
                LocalDateTime.now(), // datePosted
                Category.CONSTRUCTION_TOYS, // category
                "New description", // description
                ItemCondition.USED, // condition
                Status.AVAILABLE, // statusListing
                memberOne, // member
                null, // comments
                null // images
        );

        MockMultipartFile images = new MockMultipartFile("images", "image1.jpg", "image/jpeg", "image content 1".getBytes());



        when(mockListingService.saveListing(any(ListingDTO.class)))
                .thenReturn(expectedListing);

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/listings")
                        .file(images)
                        .param("title", newListingDTO.getTitle())
                        .param("userID", String.valueOf(newListingDTO.getMemberId()))
                        .param("category", newListingDTO.getCategory())
                        .param("description", newListingDTO.getDescription())
                        .param("condition", newListingDTO.getCondition())
                        .param("statusListing", newListingDTO.getStatusListing()))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockListingService, times(1)).saveListing(any(ListingDTO.class));
    }



    // Delete Testings
    @Test
    @DisplayName("DELETE ")
    void testDeleteListing_Success() throws Exception {
        Long listingID = 1L;

        doNothing().when(mockListingService).deleteListingById(listingID);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/listings/{listingID}", listingID))
                .andExpect(MockMvcResultMatchers.status().isAccepted());

        verify(mockListingService, times(1)).deleteListingById(listingID);
    }

    @Test
    @DisplayName("DELETE -> Delete Listing by ID Not Found")
    void testDeleteListing_NotFound() throws Exception {
        Long listingID = 1L;

        doThrow(new ListingNotFoundException("Listing with ID " + listingID + " not found"))
                .when(mockListingService).deleteListingById(listingID);

        Exception exception = assertThrows(ServletException.class, () -> {
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/listings/{listingID}", listingID))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Listing with ID " + listingID + " not found and can't be deleted"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(HttpStatus.NOT_FOUND.value()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Listing Not Found"));
        });

        verify(mockListingService, times(1)).deleteListingById(listingID);
    }


    @Test
    void testDeleteListingsByMember_Success() throws Exception {
        Long memberID = 1L;

        doNothing().when(mockListingService).deleteListingsByMember(memberID);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/listings/member/{memberID}", memberID))
                .andExpect(MockMvcResultMatchers.status().isAccepted());

        verify(mockListingService, times(1)).deleteListingsByMember(memberID);
    }

    // Update Testings
    @Test
    void testUpdateListing_Success() throws Exception {
        Listing listing = new Listing(1L, "A Update listing test", LocalDateTime.now(), Category.ACTION_FIGURES, "I am a Updated description :-)", ItemCondition.GOOD, Status.AVAILABLE, memberOne, null, null);
        when(mockListingService.updateListing(listing)).thenReturn(listing);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/listings/{id}", listing.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(listing)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void testUpdateListing_NotFound() throws Exception {
        Listing listing = new Listing(1L, "A Update listing test", LocalDateTime.now(), Category.ACTION_FIGURES, "I am a Updated description :-)", ItemCondition.GOOD, Status.AVAILABLE, memberOne, null, null);
        doThrow(new ListingNotFoundException("Listing not found")).when(mockListingService).updateListing(listing);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/listings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(listing)))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }
}