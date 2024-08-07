package springsamurais.toyswapbackend.service.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import springsamurais.toyswapbackend.config.CacheConfig;
import springsamurais.toyswapbackend.exception.MemberNotFoundException;
import springsamurais.toyswapbackend.model.Member;
import springsamurais.toyswapbackend.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class MemberServiceImplementationTest {
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberServiceImplementation memberService;

    private Member member;
    private List<Member> membersList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        member = new Member(1L, "Andrew", "Andy", "London","pass", "email",null);

        membersList = new ArrayList<>();
        membersList.add(member);
        membersList.add(new Member(2L, "Nicolas", "Nick", "Cardiff","pass", "email",null));

    }

    @Test
    void testGetAllMembers() {
        when(memberRepository.findAll()).thenReturn(membersList);

        List<Member> result = memberService.getAllMembers();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(memberRepository, times(1)).findAll();
    }

    @Test
    void testGetMemberById_Success() throws MemberNotFoundException {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));

        Member foundMember = memberService.getMemberByID(1L);

        assertNotNull(foundMember);
        assertEquals("Andrew", foundMember.getName());

        verify(memberRepository, times(1)).findById(1L);
    }

    @Test
    void testGetMemberById_NotFound() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        MemberNotFoundException exception = assertThrows(MemberNotFoundException.class, () -> {
            memberService.getMemberByID(1L);
        });

        assertEquals("Member with ID 1 not found", exception.getMessage());

        verify(memberRepository, times(1)).findById(1L);
    }

    @Test
    void testAddMember_Success() {
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        Member newMember = memberService.addMember(member);

        assertNotNull(newMember);
        assertEquals("Andrew", newMember.getName());

        verify(memberRepository, times(1)).save(member);
    }

    @Test
    void testUpdateMember_Success() throws MemberNotFoundException {
        when(memberRepository.existsById(anyLong())).thenReturn(true);
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        Member updatedMember = memberService.updateMember(1L, member);


        assertNotNull(updatedMember);
        assertEquals("Andrew", updatedMember.getName());

        verify(memberRepository, times(1)).existsById(1L);
        verify(memberRepository, times(1)).save(member);
    }

    @Test
    void testUpdateMember_NotFound() {
        when(memberRepository.existsById(anyLong())).thenReturn(false);

        MemberNotFoundException exception = assertThrows(MemberNotFoundException.class, () -> {
            memberService.updateMember(1L, member);
        });

        assertEquals("Member with ID 1 not found", exception.getMessage());

        verify(memberRepository, times(1)).existsById(1L);
        verify(memberRepository, times(0)).save(any(Member.class));
    }

    @Test
    void testDeleteMemberByID_Success() throws MemberNotFoundException {
        when(memberRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(memberRepository).deleteById(anyLong());

        memberService.deleteMemberByID(1L);

        verify(memberRepository, times(1)).existsById(1L);
        verify(memberRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteMemberByID_NotFound() {
        when(memberRepository.existsById(anyLong())).thenReturn(false);

        MemberNotFoundException exception = assertThrows(MemberNotFoundException.class, () -> {
            memberService.deleteMemberByID(1L);
        });

        assertEquals("Member with ID 1 not found", exception.getMessage());

        verify(memberRepository, times(1)).existsById(1L);
        verify(memberRepository, times(0)).deleteById(anyLong());
    }


    // Caching Testings

    @Test
    public void testGetMemberByID_CacheHit() throws MemberNotFoundException {
        // Mock data
        Long memberId = 1L;
        Member mockMember = new Member(memberId, "Test Member", "test", "something", "Location","pass", new ArrayList<>());

        // Mock repository method to return a member
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(mockMember));

        // First invocation: Retrieve member (should hit repository)
        Member retrievedMember1 = memberService.getMemberByID(memberId);

        // Second invocation: Retrieve member again (should hit cache, not repository)
        Member retrievedMember2 = memberService.getMemberByID(memberId);

        // Verify repository method called only once
        verify(memberRepository, times(2)).findById(memberId);

        // Verify cached member is returned
        assertEquals(memberId, retrievedMember1.getId());
        assertEquals(memberId, retrievedMember2.getId());
    }

    @Test
    public void testGetMemberByID_CacheMiss() throws MemberNotFoundException {
        // Mock data
        Long memberId = 1L;

        // Mock repository method to return empty optional (simulating member not found)
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        // Call the service method and assert that MemberNotFoundException is thrown
        MemberNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(MemberNotFoundException.class, () -> {
            memberService.getMemberByID(memberId);
        });

        // Verify repository method called only once
        verify(memberRepository, times(1)).findById(memberId);

        // Verify exception message
        assertEquals("Member with ID " + memberId + " not found", exception.getMessage());
    }
}