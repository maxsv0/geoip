package com.max.appengine.springboot.geoip.model;

import java.util.List;

public class GoogleGeocode {
  private List<GoogleGeocodeResult> results;

  public List<GoogleGeocodeResult> getResults() {
    return results;
  }

  public void setResults(List<GoogleGeocodeResult> results) {
    this.results = results;
  }

  @Override
  public String toString() {
    return "GoogleGeocode [results=" + results + "]";
  }
  
}
