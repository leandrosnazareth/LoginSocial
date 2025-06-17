package com.leandrosnazareth.LoginSocial.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leandrosnazareth.LoginSocial.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByProviderAndProviderId(String provider, String providerId);
}