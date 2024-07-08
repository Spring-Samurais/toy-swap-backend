package springsamurais.toyswapbackend.service.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springsamurais.toyswapbackend.exception.MemberNotFoundException;
import springsamurais.toyswapbackend.model.Member;
import springsamurais.toyswapbackend.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class MemberServiceImplementation implements MemberService {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
//    @Cacheable(value = "members", key = "#memberID")
    public Member getMemberByID(Long memberID) throws MemberNotFoundException {
        return memberRepository.findById(memberID)
                .orElseThrow(() -> new MemberNotFoundException("Member with ID " + memberID + " not found"));
    }

    @Override
//    @Cacheable(value = "members")
    public List<Member> getAllMembers() {
        List<Member> membersListResult = new ArrayList<>();
        memberRepository.findAll().forEach(membersListResult::add);
        return membersListResult;
    }

    @Override
    public Member addMember(Member member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
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

        if (!memberRepository.existsById(memberID)) {
            throw new MemberNotFoundException("Member with ID " + memberID + " not found");
        }
        memberRepository.deleteById(memberID);
    }
}

