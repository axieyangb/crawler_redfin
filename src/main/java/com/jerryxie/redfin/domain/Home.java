package com.jerryxie.redfin.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Home")
public class Home {
    @Override
    public String toString() {
        return "Home [id=" + id + ", soldDate=" + soldDate + ", location=" + location + ", city=" + city + ", zip="
                + zip + ", price=" + price + ", beds=" + beds + ", baths=" + baths + ", squareFeet=" + squareFeet
                + ", lotSize=" + lotSize + ", yearBuild=" + yearBuild + ", daysOnMarket=" + daysOnMarket
                + ", dollarPerSquareFeet=" + dollarPerSquareFeet + ", hoa=" + hoa + ", url=" + url + ", latitude="
                + latitude + ", longitude=" + longitude + "]";
    }

    @Id
    private String id;
    @Field("sold_date")
    private String soldDate;

    public String getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(String soldDate) {
        this.soldDate = soldDate;
    }

    private String location;
    private String city;
    private int zip;
    private int price;
    private double beds;
    private double baths;
    @Field("square_feet")
    private int squareFeet;
    @Field("lot_size")
    private int lotSize;
    @Field("year_build")
    private int yearBuild;
    @Field("days_on_market")
    private int daysOnMarket;
    @Field("dollar_per_square_feet")
    private double dollarPerSquareFeet;
    private double hoa;
    private String url;
    private float latitude;
    private float longitude;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getBeds() {
        return beds;
    }

    public void setBeds(double beds) {
        this.beds = beds;
    }

    public double getBaths() {
        return baths;
    }

    public void setBaths(double baths) {
        this.baths = baths;
    }

    public int getSquareFeet() {
        return squareFeet;
    }

    public void setSquareFeet(int squareFeet) {
        this.squareFeet = squareFeet;
    }

    public int getLotSize() {
        return lotSize;
    }

    public void setLotSize(int lotSize) {
        this.lotSize = lotSize;
    }

    public int getYearBuild() {
        return yearBuild;
    }

    public void setYearBuild(int yearBuild) {
        this.yearBuild = yearBuild;
    }

    public int getDaysOnMarket() {
        return daysOnMarket;
    }

    public void setDaysOnMarket(int daysOnMarket) {
        this.daysOnMarket = daysOnMarket;
    }

    public double getDollarPerSquareFeet() {
        return dollarPerSquareFeet;
    }

    public void setDollarPerSquareFeet(double dollarPerSquareFeet) {
        this.dollarPerSquareFeet = dollarPerSquareFeet;
    }

    public double getHoa() {
        return hoa;
    }

    public void setHoa(double hoa) {
        this.hoa = hoa;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

}
