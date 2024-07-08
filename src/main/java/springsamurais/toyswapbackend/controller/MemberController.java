package springsamurais.toyswapbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springsamurais.toyswapbackend.exception.*;
import springsamurais.toyswapbackend.model.Member;
import springsamurais.toyswapbackend.model.MemberDTO;
import springsamurais.toyswapbackend.service.member.MemberService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("")
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> memberList = memberService.getAllMembers();
        return new ResponseEntity<>(memberList, HttpStatus.OK);
    }

    @GetMapping("/{memberID}")
    public ResponseEntity<?> getMemberById(@PathVariable("memberID") Long memberID) {
        try {
            Member memberFound = memberService.getMemberByID(memberID);
            return new ResponseEntity<>(memberFound, HttpStatus.OK);
        } catch (MemberNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Member> addMember(@RequestBody Member member) {
        Member newMember = memberService.addMember(member);
        return new ResponseEntity<>(newMember, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDTO userLogin) {
        try {
           Member foundMember =  memberService.loginChecker(userLogin.username(), userLogin.password());
            return new ResponseEntity<>(foundMember, HttpStatus.OK);
        } catch (MemberNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/{memberID}")
    public ResponseEntity<?> updateMember(@PathVariable("memberID") Long memberID, @RequestBody Member member) {
        try {
            Member updatedMember = memberService.updateMember(memberID, member);
            return new ResponseEntity<>(updatedMember, HttpStatus.OK);
        } catch (MemberNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{memberID}")
    public ResponseEntity<?> deleteMemberById(@PathVariable("memberID") Long memberID) {
        try {
            memberService.deleteMemberByID(memberID);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (MemberNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
