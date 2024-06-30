package springsamurais.toyswapbackend.service.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springsamurais.toyswapbackend.exception.MemberNotFoundException;
import springsamurais.toyswapbackend.model.Member;
import springsamurais.toyswapbackend.repository.MemberRepository;



@Service
public class MemberServiceImplementation implements MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Override
    public Member getMemberByID(Long memberID) throws MemberNotFoundException {
        return memberRepository.findById(memberID)
                .orElseThrow(() -> new MemberNotFoundException("Member with ID " + memberID + " not found"));
    }
}
