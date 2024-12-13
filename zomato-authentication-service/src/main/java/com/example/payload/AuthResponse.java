package com.example.payload;

public class AuthResponse {

    private String accessToken;
    private String tokenType = "Bearer";
    private String tokenId;

    public AuthResponse() {
    }

    public AuthResponse(String accessToken, String tokenType, String tokenId) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.tokenId = tokenId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
