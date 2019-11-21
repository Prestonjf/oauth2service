package com.prestonsproductions.oauth2service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prestonsproductions.oauth2service.domain.OauthClientDetail;

public interface OauthClientDetailRepository extends JpaRepository<OauthClientDetail, Integer> {

}
