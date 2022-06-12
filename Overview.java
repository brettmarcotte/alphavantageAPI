package com.careerdevs.stockapiv1.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Overview {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @JsonProperty("Symbol")
    @Column(name = "symbol", nullable = false, unique = true)
    private String symbol;

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
    private String industry;

    @JsonProperty("MarketCapitalization")
    @Column(name = "market_cap", nullable = false)
    private String marketCap;

    @JsonProperty("Exchange")
    @Column(name = "exchange", nullable = false)
    private String exchange;

    @JsonProperty("52WeekHigh")
    @Column(name="year_High", nullable = false)
    private String yearHigh;

    @JsonProperty("52WeekLow")
    @Column(name = "year_Low", nullable = false)
    private String yearLow;

    @JsonProperty("DividendDate")
    @Column(name="dividendDate", nullable = false)
    private String dividendDate;

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"symbol\":\"" + symbol + '"' +
                ", \"assetType\":\"" + assetType + '"' +
                ", \"name\":\"" + name + '"' +
                ", \"currency\":\"" + currency + '"' +
                ", \"country\":\"" + country + '"' +
                ", \"sector\":\"" + sector + '"' +
                ", \"industrty\":\"" + industry + '"' +
                ", \"marketcap\":\"" + marketCap + '"' +
                ", \"exchange\":\"" + exchange + '"' +
                ", \"yearHigh\":\"" + yearHigh + '"' +
                ", \"yearLow\":\"" + yearLow + '"' +
                ", \"dividendDate\":\"" + dividendDate + '"' +
                '}';
    }

    public long getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getAssetType() {
        return assetType;
    }

    public String getName() {
        return name;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCountry() {
        return country;
    }

    public String getSector() {
        return sector;
    }

    public String getIndustrty() {
        return industry;
    }

    public String getMarketcap() {
        return marketCap;
    }

    public String getExchange() {
        return exchange;
    }

    public String getYearHigh() {
        return yearHigh;
    }

    public String getYearLow() {
        return yearLow;
    }

    public String getDividendDate() {
        return dividendDate;
    }
}
