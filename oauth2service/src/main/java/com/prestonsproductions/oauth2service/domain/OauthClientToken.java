package com.prestonsproductions.oauth2service.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="oauth_client_token")
@NamedQuery(name="OauthClientToken.findAll", query="SELECT o FROM OauthClientToken o")
public class OauthClientToken implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="authentication_id")
	private String authenticationId;

	@Column(name="client_id")
	private String clientId;

	@Lob
	private byte[] token;

	@Column(name="token_id")
	private String tokenId;

	@Column(name="user_name")
	private String userName;

	public OauthClientToken() {
	}

	public String getAuthenticationId() {
		return this.authenticationId;
	}

	public void setAuthenticationId(String authenticationId) {
		this.authenticationId = authenticationId;
	}

	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
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

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}