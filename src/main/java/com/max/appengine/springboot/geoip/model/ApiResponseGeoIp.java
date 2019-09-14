package com.max.appengine.springboot.geoip.model;

import java.util.Date;

public class ApiResponseGeoIp extends ApiResponseBase {
  private boolean ok;

  private String location;

  private String country;

  private String cityLatLong;

  private Date date;

  private Locale locale;

  public ApiResponseGeoIp(String location, String country, String cityLatLong, Locale locale) {
    super("");
    this.ok = true;
    this.location = location;
    this.country = country;
    this.cityLatLong = cityLatLong;
    this.date = new Date();
    this.locale = locale;
  }

  public boolean isOk() {
    return ok;
  }

  public void setOk(boolean ok) {
    this.ok = ok;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getCityLatLong() {
    return cityLatLong;
  }

  public void setCityLatLong(String cityLatLong) {
    this.cityLatLong = cityLatLong;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Locale getLocale() {
    return locale;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }
}
