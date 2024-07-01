package springsamurais.toyswapbackend.service.member;

import springsamurais.toyswapbackend.exception.MemberNotFoundException;
import springsamurais.toyswapbackend.model.Member;

import java.util.List;


public interface MemberService {

    Member getMemberByID(Long memberID) throws MemberNotFoundException;

    List<Member> getAllMembers();
    Member addMember(Member member);
    Member updateMember(Long memberID, Member member) throws MemberNotFoundException;
    void deleteMemberById(Long memberID) throws MemberNotFoundException;
}
