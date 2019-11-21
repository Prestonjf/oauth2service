package com.prestonsproductions.oauth2service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prestonsproductions.oauth2service.domain.OauthAccessToken;

public interface OauthAccessTokenRepository extends JpaRepository<OauthAccessToken, Integer> {

}
