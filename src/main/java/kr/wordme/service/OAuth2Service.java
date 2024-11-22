package kr.wordme.service;

import kr.wordme.exception.ErrorCode;
import kr.wordme.exception.member.MemberException;
import kr.wordme.model.dto.OAuth2UserInfo;
import kr.wordme.model.entity.Member;
import kr.wordme.repository.MemberRepository;
import kr.wordme.util.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2Service extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @SneakyThrows
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(provider, oAuth2User.getAttributes());

        Member member = memberRepository.findByEmail(oAuth2UserInfo.getEmail()).orElseGet(()-> memberRepository.save(oAuth2UserInfo.toEntity()));

        if(!member.isEnabled() || ObjectUtils.isEmpty(member)) {
            throw new MemberException(
                    ErrorCode.NOT_EXIST_USER.getStatus(),
                    ErrorCode.NOT_EXIST_USER.getMessage()
            );
        }
        return new CustomUserDetails(member, oAuth2User.getAttributes());
    }
}
