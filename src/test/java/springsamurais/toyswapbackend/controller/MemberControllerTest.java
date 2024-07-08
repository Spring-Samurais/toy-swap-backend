package springsamurais.toyswapbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import springsamurais.toyswapbackend.exception.MemberNotFoundException;
import springsamurais.toyswapbackend.model.Member;
import springsamurais.toyswapbackend.service.member.MemberService;

import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;

class MemberControllerTest {

    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberController memberController;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();
    private Member memberOne;
    private List<Member> membersList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
        memberOne = new Member(1L, "Andrew", "Andy", "London", "password", null);
        Member memberTwo = new Member(2L, "Samuel", "Sam", "Wembley", "password",null);

        membersList = Arrays.asList(memberOne, memberTwo);
    }

    @Test
    void testGetAllMembers() throws Exception {
        when(memberService.getAllMembers()).thenReturn(membersList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/members"))
                .andExpectAll(MockMvcResultMatchers.status().isOk())
                .andExpectAll(MockMvcResultMatchers.content().json(mapper.writeValueAsString(membersList)))
                .andExpectAll(MockMvcResultMatchers.jsonPath("$.length()").value(membersList.size()));

        verify(memberService, times(1)).getAllMembers();
    }

    @Test
    void testGetMemberById_Success() throws Exception {
        when(memberService.getMemberByID(anyLong())).thenReturn(memberOne);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/members/{memberId}", 1L))
                .andExpectAll(MockMvcResultMatchers.status().isOk())
                .andExpectAll(MockMvcResultMatchers.jsonPath("$.name").value("Andrew"));

        verify(memberService, times(1)).getMemberByID(1L);
    }

    @Test
    void testGetMemberById_NotFound() throws Exception {
        when(memberService.getMemberByID(anyLong())).thenThrow(new MemberNotFoundException("Member with ID 1 not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/members/{memberId}", 1L))
                .andExpectAll(MockMvcResultMatchers.status().isNotFound())
                .andExpectAll(MockMvcResultMatchers.content().string("Member with ID 1 not found"));

        verify(memberService, times(1)).getMemberByID(1L);
    }

    @Test
    void testAddMember() throws Exception {

    }

    @Test
    void testUpdateMember_Success() throws Exception {
//        memberOne = new Member(1L, "Jackson", "Jack", "London", null);
//        membersList.add(memberOne);
        when(memberService.updateMember(anyLong(), any(Member.class))).thenReturn(memberOne);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/members/{memberId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(memberOne)))
                .andExpectAll(MockMvcResultMatchers.status().isOk())
                .andExpectAll(MockMvcResultMatchers.jsonPath("$.name").value("Andrew"));

        verify(memberService, times(1)).updateMember(eq(1L), any(Member.class));
    }

    @Test
    void testUpdateMember_NotFound() throws Exception {
        when(memberService.updateMember(anyLong(), any(Member.class))).thenThrow(new MemberNotFoundException("Member not found"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/members/{memberId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(memberOne)))
                .andExpectAll(MockMvcResultMatchers.status().isNotFound())
                .andExpectAll(MockMvcResultMatchers.content().string("Member not found"));

        verify(memberService, times(1)).updateMember(eq(1L), any(Member.class));
    }

    @Test
    void testDeleteMemberById_Success() throws Exception {
        doNothing().when(memberService).deleteMemberByID(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/members/{memberId}", 1L))
                .andExpectAll(MockMvcResultMatchers.status().isAccepted());

        verify(memberService, times(1)).deleteMemberByID(1L);
    }

    @Test
    void testDeleteMemberById_NotFound() throws Exception {
        doThrow(new MemberNotFoundException("Member not found")).when(memberService).deleteMemberByID(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/members/{memberId}", 1L))
                .andExpectAll(MockMvcResultMatchers.status().isNotFound())
                .andExpectAll(MockMvcResultMatchers.content().string("Member not found"));

        verify(memberService, times(1)).deleteMemberByID(1L);
    }



}