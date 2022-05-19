package com.careerdevs.stockapiv1.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Overview {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @JsonProperty("Symbol")
    @Column(name = "symbol", nullable = false, unique = true)
    private String Symbol;

    @JsonProperty("AssetType")
    @Column(name = "asset_type", nullable = false)
    private String assetType;

    @JsonProperty("Name")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @JsonProperty("NASDAQ")
    @Column(name = "Currency", nullable = false)
    private String currency;

    @JsonProperty("Country")
    @Column(name = "country", nullable = false)
    private String country;

    @JsonProperty("Sector")
    @Column(name = "sector", nullable = false)
    private String sector;

    @JsonProperty("Industry")
    @Column(name = "industry", nullable = false)
    private String industrty;

    @JsonProperty("MarketCapitalization")
    @Column(name = "market_cap", nullable = false)
    private String marketcap;

    @JsonProperty("52WeekHigh")
    @Column(name="year_low", nullable = false)
    private String yearLow;

    @JsonProperty("DividendDate")
    @Column(name="dividendDate", nullable = false)
    private String dividendDate;


    public Object getSymbol() {
        return null;
    }
}
