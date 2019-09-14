package com.max.appengine.springboot.geoip.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GoogleGeocodeResult {
  @JsonProperty("formatted_address")
  private String formattedAddress;

  public String getFormattedAddress() {
    return formattedAddress;
  }

  public void setFormattedAddress(String formattedAddress) {
    this.formattedAddress = formattedAddress;
  }

  @Override
  public String toString() {
    return "GoogleGeocodeResult [formattedAddress=" + formattedAddress + "]";
  }
  
}
