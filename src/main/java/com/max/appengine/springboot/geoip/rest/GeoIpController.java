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

package com.max.appengine.springboot.geoip.rest;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.max.appengine.springboot.geoip.model.ApiResponseBase;
import com.max.appengine.springboot.geoip.service.GeoIpService;

@CrossOrigin
@RestController
public class GeoIpController extends AbstractApiController {

  private final GeoIpService geoIpService;

  @Autowired
  public GeoIpController(GeoIpService geoIpService) {
    this.geoIpService = geoIpService;
  }

  @RequestMapping(value = "/ip", method = RequestMethod.GET)
  public ResponseEntity<ApiResponseBase> getLocationFromIp(HttpServletRequest request) {

    Optional<String> location = geoIpService.getLocationFromIp(getIp(request));
    if (location.isPresent()) {
      return sendResponseBase(location.get());
    } else {
      return sendResponseError("GeoIp Service not ready");
    }
  }

  @RequestMapping(value = "/geoip", method = RequestMethod.GET)
  public ResponseEntity<ApiResponseBase> status(HttpServletRequest request,
      Optional<String> action) {
    StringBuilder status = new StringBuilder();
    status.append("Your IP: " + getIp(request) + "\n");
    status.append(geoIpService.getDatabaseFileStatus() + "\n");
    status.append(geoIpService.getDatabaseReaderStatus());

    if (action.isPresent() && action.get().equals("reload")) {
      status.append("Service reloaded\n");
      try {
        geoIpService.initGeoIpDatabase();
      } catch (IOException e) {
        status.append("Reload failed\n");
      }

      status.append(geoIpService.getDatabaseFileStatus() + "\n");
      status.append(geoIpService.getDatabaseReaderStatus());
    }

    return sendResponseBase(status.toString());
  }
}
