package springsamurais.toyswapbackend.service.member;

import springsamurais.toyswapbackend.exception.MemberNotFoundException;
import springsamurais.toyswapbackend.model.Member;
import java.util.Optional;

public interface MemberService {

    Member getMemberByID(Long memberID) throws MemberNotFoundException;
}
