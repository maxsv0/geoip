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

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.google.appengine.api.utils.SystemProperty;
import com.max.appengine.springboot.geoip.model.ApiResponseBase;

@RestController
public class IndexController extends AbstractApiController {
  public static final String VERSION_NAME = "Geo IP API v.1";
  
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ResponseEntity<ApiResponseBase> index(HttpServletRequest request) {
    String version = SystemProperty.version.get();
    String applicationVersion = SystemProperty.applicationVersion.get();
    return sendResponseBase(VERSION_NAME + " Build " + applicationVersion + " " + version);
  }
}
