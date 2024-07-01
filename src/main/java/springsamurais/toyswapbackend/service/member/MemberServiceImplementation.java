package springsamurais.toyswapbackend.service.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springsamurais.toyswapbackend.exception.MemberNotFoundException;
import springsamurais.toyswapbackend.model.Member;
import springsamurais.toyswapbackend.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class MemberServiceImplementation implements MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Override
    public Member getMemberByID(Long memberID) throws MemberNotFoundException {
        return memberRepository.findById(memberID)
                .orElseThrow(() -> new MemberNotFoundException("Member with ID " + memberID + " not found"));
    }

    @Override
    public List<Member> getAllMembers() {
        List<Member> membersListResult = new ArrayList<>();
        memberRepository.findAll().forEach(membersListResult::add);
        return membersListResult;
    }

    @Override
    public Member addMember(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member updateMember(Long memberID, Member member) throws MemberNotFoundException {
        if (!memberRepository.existsById(memberID)) {
            throw new MemberNotFoundException("Member with ID " + memberID + " not found");
        }
        member.setId(memberID);
        return memberRepository.save(member);
    }

    @Override
    public void deleteMemberByID(Long memberID) throws MemberNotFoundException {
//        Optional<Member> members = memberRepository.findById(memberID);
//        if (members.isEmpty()) {
//            throw new MemberNotFoundException("Member with ID " + memberID + " not found");
//        }
//        memberRepository.deleteById(memberID);
//    }
        if (!memberRepository.existsById(memberID)) {
            throw new MemberNotFoundException("Member with ID " + memberID + " not found");
        }
        memberRepository.deleteById(memberID);
    }

    /*List<Listing> listings = listingRepository.findByMemberId(memberID);
        if (listings.isEmpty()) {
            throw new MemberNotFoundException("Listing with Member ID " + memberID + " not found");
        }
        listingRepository.deleteAll(listings);*/
}

