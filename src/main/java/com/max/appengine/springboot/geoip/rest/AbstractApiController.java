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

import java.util.Enumeration;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.max.appengine.springboot.geoip.model.ApiResponseBase;
import com.max.appengine.springboot.geoip.model.ApiResponseError;

public abstract class AbstractApiController {
  public static final String MESSAGE_INVALID_ACCESS = "Access denied, Please log in and try again";

  public static final String MESSAGE_WRONG_REQUEST = "Wrong request";

  protected ResponseEntity<ApiResponseBase> sendResponseError(String message) {
    return sendResponseOk(new ApiResponseError(message));
  }

  protected ResponseEntity<ApiResponseBase> sendResponseBase(String message) {
    return sendResponseOk(new ApiResponseBase(message));
  }

  protected ResponseEntity<ApiResponseBase> sendResponseOk(ApiResponseBase response) {
    return new ResponseEntity<ApiResponseBase>(response, HttpStatus.OK);
  }

  protected String getIp(HttpServletRequest request) {
    String ipProxy = request.getHeader("X-FORWARDED-FOR");
    if (ipProxy == null) {
      return request.getRemoteAddr();
    }
    return ipProxy;
  }

  protected Optional<String> getTokenFromHeader(HttpServletRequest request) {
    Enumeration<String> headers = request.getHeaders("Authorization");
    while (headers.hasMoreElements()) { // typically there is only one (most servers enforce that)
      String value = headers.nextElement();
      if (value != null && !value.isEmpty() && value.toLowerCase().startsWith("bearer")) {
        return Optional.of(value.substring(6).trim());
      }
    }
    return Optional.empty();
  }
}
