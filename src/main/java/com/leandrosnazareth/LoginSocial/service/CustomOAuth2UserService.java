package com.leandrosnazareth.LoginSocial.service;

import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.leandrosnazareth.LoginSocial.model.User;
import com.leandrosnazareth.LoginSocial.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        try {
            OAuth2User oAuth2User = super.loadUser(userRequest);
            processOAuth2User(userRequest, oAuth2User);
            return oAuth2User;
        } catch (OAuth2AuthenticationException ex) {
            logger.error("Falha na autenticação OAuth2", ex);
            throw ex;
        } catch (Exception ex) {
            logger.error("Erro inesperado durante o processamento do usuário OAuth2", ex);
            throw new OAuth2AuthenticationException("Erro ao processar usuário OAuth2");
        }
    }

    private void processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String provider = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        
        UserInfo userInfo = extractUserInfo(provider, attributes);
        
        userRepository.findByProviderAndProviderId(provider, userInfo.providerId())
                .orElseGet(() -> createNewUser(provider, userInfo, attributes));
    }

    private UserInfo extractUserInfo(String provider, Map<String, Object> attributes) {
        return switch (provider.toLowerCase()) {
            case "google" -> new UserInfo(
                    (String) attributes.get("sub"),
                    (String) attributes.get("name"),
                    (String) attributes.get("email"));
            case "facebook" -> new UserInfo(
                    (String) attributes.get("id"),
                    (String) attributes.get("name"),
                    (String) attributes.get("email"));
            default -> throw new IllegalArgumentException("Provedor não suportado: " + provider);
        };
    }

    private User createNewUser(String provider, UserInfo userInfo, Map<String, Object> attributes) {
        User newUser = new User();
        newUser.setProvider(provider);
        newUser.setProviderId(userInfo.providerId());
        newUser.setName(userInfo.name());
        newUser.setEmail(userInfo.email());
        newUser.setAttributes(convertAttributes(attributes));
        
        logger.info("Criando novo usuário para {}: {}", provider, userInfo.email());
        return userRepository.save(newUser);
    }

    private Map<String, String> convertAttributes(Map<String, Object> attributes) {
        return attributes.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue() != null ? 
                                entry.getValue().toString() : 
                                "null"));
    }

    private record UserInfo(String providerId, String name, String email) {}
}