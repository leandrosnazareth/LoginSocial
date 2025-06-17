package com.leandrosnazareth.LoginSocial.controller;

import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.leandrosnazareth.LoginSocial.model.User;
import com.leandrosnazareth.LoginSocial.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class MainController {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    public MainController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            try {
                String provider = determineProvider(principal.getAttributes());
                String providerId = getProviderId(provider, principal.getAttributes());
                String name = principal.getAttribute("name");
                String email = principal.getAttribute("email");
                Map<String, Object> attributes = principal.getAttributes();

                // Busca ou cria o usuário no banco de dados
                User user = userRepository.findByProviderAndProviderId(provider, providerId)
                        .orElseGet(() -> {
                            User newUser = new User();
                            newUser.setProvider(provider);
                            newUser.setProviderId(providerId);
                            newUser.setName(name);
                            newUser.setEmail(email);
                            newUser.setAttributes(convertAttributesToStringValues(attributes));
                            return userRepository.save(newUser);
                        });

                // Adiciona atributos ao modelo
                model.addAttribute("name", user.getName());
                model.addAttribute("email", user.getEmail());
                model.addAttribute("provider", user.getProvider());
                model.addAttribute("user", user);

                logger.info("Usuário {} autenticado via {} (ID: {})", name, provider, providerId);

            } catch (Exception e) {
                logger.error("Erro ao processar perfil do usuário", e);
                model.addAttribute("error", "Erro ao carregar perfil");
            }
        }
        return "profile";
    }

    private String determineProvider(Map<String, Object> attributes) {
        if (attributes.containsKey("sub")) {
            return "google";
        } else if (attributes.containsKey("id")) {
            return "facebook";
        }
        return "unknown";
    }

    private String getProviderId(String provider, Map<String, Object> attributes) {
        return switch (provider.toLowerCase()) {
            case "google" -> (String) attributes.get("sub");
            case "facebook" -> (String) attributes.get("id");
            default -> null;
        };
    }

    private Map<String, String> convertAttributesToStringValues(Map<String, Object> attributes) {
        return attributes.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue() != null ? entry.getValue().toString() : "null"));
    }

    @GetMapping("/logout")
    public String performLogout(Authentication authentication,
            HttpServletRequest request,
            HttpServletResponse response) {
        // Realiza o logout programaticamente
        this.logoutHandler.logout(request, response, authentication);

        logger.info("Usuário {} realizou logout", authentication != null ? authentication.getName() : "N/A");
        return "redirect:/login?logout"; // Redireciona para a página de login com mensagem
    }
}