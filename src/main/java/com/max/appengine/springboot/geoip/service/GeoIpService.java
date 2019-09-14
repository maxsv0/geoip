/*
 * Copyright 2018 Max Svistunov
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.max.appengine.springboot.geoip.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.max.appengine.springboot.geoip.model.GoogleGeocode;
import com.max.appengine.springboot.geoip.model.Locale;

@Service
public class GeoIpService {
  public static final String API_URL = "https://maps.googleapis.com/maps/api/geocode/json"
      + "?latlng={latlng}&key={key}&language={language}&result_type=locality";

  public static final String API_KEY = "";

  private final ObjectMapper mapper = new ObjectMapper();

  @Autowired
  public GeoIpService() {
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  public String getLocationFromCoordinates(String latlng, Locale locale) {

    String requestUrl = API_URL;
    requestUrl = requestUrl.replace("{latlng}", latlng);
    requestUrl = requestUrl.replace("{key}", API_KEY);
    requestUrl = requestUrl.replace("{language}", locale.toString().toLowerCase());

    RequestConfig requestConfig =
        RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();

    HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
    HttpGet request = new HttpGet(requestUrl);

    try {
      HttpResponse response = client.execute(request);

      BufferedReader rd =
          new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

      StringBuffer result = new StringBuffer();
      String line = "";

      while ((line = rd.readLine()) != null) {
        result.append(line);
      }

      GoogleGeocode geoCode = parsePayload(result.toString());

      return geoCode.getResults().get(0).getFormattedAddress();

    } catch (IOException error) {
      return null;
    }
  }

  private GoogleGeocode parsePayload(String jsonNodeString) throws IOException {
    JsonNode resultNode = mapper.readTree(jsonNodeString);
    System.out.println(jsonNodeString);
    System.out.println(resultNode);

    GoogleGeocode result = mapper.convertValue(resultNode, GoogleGeocode.class);
    System.out.println(result);
    return result;

  }

}
