
package com.prestonsproductions.oauth2service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prestonsproductions.oauth2service.domain.ClientDetail;


public interface ClientDetailRepository extends JpaRepository<ClientDetail, String> {

}
