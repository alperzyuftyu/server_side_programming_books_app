package be.thomasmore.bookserver.services;


import be.thomasmore.bookserver.model.Member;
import be.thomasmore.bookserver.model.converters.MemberDetailedDTOConverter;
import be.thomasmore.bookserver.model.dto.MemberDetailedDTO;
import be.thomasmore.bookserver.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberDetailedDTOConverter memberDetailedDTOConverter;

    public MemberDetailedDTO findById(int id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));

        return memberDetailedDTOConverter.convertToDTO(member);
    }

    public MemberDetailedDTO findByUsername(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));
        return memberDetailedDTOConverter.convertToDTO(member);
    }
}
