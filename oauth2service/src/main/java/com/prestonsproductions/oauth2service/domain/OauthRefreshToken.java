package com.prestonsproductions.oauth2service.domain;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name="oauth_refresh_token")
@NamedQuery(name="OauthRefreshToken.findAll", query="SELECT o FROM OauthRefreshToken o")
public class OauthRefreshToken implements Serializable {
	private static final long serialVersionUID = 1L;

	@Lob
	private byte[] authentication;

	@Lob
	private byte[] token;

	@Id
	@Column(name="token_id")
	private String tokenId;

	public OauthRefreshToken() {
	}

	public byte[] getAuthentication() {
		return this.authentication;
	}

	public void setAuthentication(byte[] authentication) {
		this.authentication = authentication;
	}

	public byte[] getToken() {
		return this.token;
	}

	public void setToken(byte[] token) {
		this.token = token;
	}

	public String getTokenId() {
		return this.tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

}