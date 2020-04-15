package com.example.Project.ECommerce.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tokenId;
    private String tokenName;
    private String randomToken;
    private long timeInMilli;

    public Token() {
    }

    public Token(long tokenId, String tokenName, String randomToken, long timeInMilli) {
        this.tokenId = tokenId;
        this.tokenName = tokenName;
        this.randomToken = randomToken;
        this.timeInMilli = timeInMilli;
    }

    public long getTokenId() {
        return tokenId;
    }

    public void setTokenId(long tokenId) {
        this.tokenId = tokenId;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getRandomToken() {
        return randomToken;
    }

    public void setRandomToken(String randomToken) {
        this.randomToken = randomToken;
    }

    public long getTimeInMilli() {
        return timeInMilli;
    }

    public void setTimeInMilli(long timeInMilli) {
        this.timeInMilli = timeInMilli;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenId=" + tokenId +
                ", tokenName='" + tokenName + '\'' +
                ", randomToken='" + randomToken + '\'' +
                ", timeInMilli=" + timeInMilli +
                '}';
    }
}
