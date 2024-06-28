package springsamurais.toyswapbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import springsamurais.toyswapbackend.exception.ListingNotFoundException;
import springsamurais.toyswapbackend.model.*;
import springsamurais.toyswapbackend.service.ListingServiceImplementation;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        memberOne = new Member(1L, "Test Member", "member", "location", null);
    }


    @Test
    @DisplayName("GET -> All Listings")
    void getAllItemsTest() throws Exception {
        Listing listing = new Listing(1L, "A listing test", null, memberOne, null, Category.ACTION_FIGURES, "I am a description :-)", ItemCondition.GOOD, Status.AVAILABLE, null);

        Listing listing2 = new Listing(2L, "Another listing test", null, memberOne, null, Category.DOLLS, "Description2", ItemCondition.LIKE_NEW, Status.PENDING, null);

        List<Listing> listings = Arrays.asList(listing, listing2);

        when(mockListingService.getAllListings()).thenReturn(listings);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/listings"))
                .andExpectAll(MockMvcResultMatchers.status().isOk())
                .andExpectAll(MockMvcResultMatchers.content().json(mapper.writeValueAsString(listings)));
    }

    @Test
    @DisplayName("GET -> ByID")
    void getByIdTest() throws Exception {
        Listing listing = new Listing(1L, "A listing test", null, memberOne, null, Category.ACTION_FIGURES, "I am a description :-)", ItemCondition.GOOD, Status.AVAILABLE, null);
        when(mockListingService.getListingById(1L)).thenReturn(listing);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/listings/1"))
                .andExpectAll(MockMvcResultMatchers.status().isOk())
                .andExpectAll(MockMvcResultMatchers.content().json(mapper.writeValueAsString(listing)));
    }

    @Test
    @DisplayName("GET -> ByID  throws 404 and message ")
    void testGetListingByIdWhenListingDoesNotExistThenReturnNotFound() throws Exception {

        Long listingId = 1L;
        when(mockListingService.getListingById(listingId)).thenThrow(new ListingNotFoundException("Listing with ID " + listingId + " not found"));


        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/listings/{listingID}", listingId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Listing with ID " + listingId + " not found"));

        verify(mockListingService, times(1)).getListingById(listingId);
    }


    // Delete Testings
    @Test
    void testDeleteListing_Success() throws Exception {
        Long listingID = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/listings/{listingID}", listingID))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(mockListingService).deleteListingById(listingID);
    }

    @Test
    void testDeleteListing_NotFound() throws Exception {
        Long listingID = 1L;
        doThrow(new ListingNotFoundException("Listing not found")).when(mockListingService).deleteListingById(listingID);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/listings/{listingID}", listingID))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeleteListingsByMember_Success() throws Exception {
        Long memberID = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/listings/member/{memberID}", memberID))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(mockListingService).deleteListingsByMember(memberID);
    }

    @Test
    void testDeleteListingsByMember_NotFound() throws Exception {
        Long memberID = 1L;
        doThrow(new ListingNotFoundException("No listings found for member")).when(mockListingService).deleteListingsByMember(memberID);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/listings/member/{memberID}", memberID))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}