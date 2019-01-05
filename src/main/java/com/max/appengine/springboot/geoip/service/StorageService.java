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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.stereotype.Service;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@Service
public class StorageService {
  public static final String GCS_BUCKET = "max-geoip";

  private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

  public void fetchFile(String fileName, File file) throws IOException {
    BlobKey blobKey = blobstoreService.createGsBlobKey("/gs/" + GCS_BUCKET + "/" + fileName);
    
    // TODO: rewrite this procedure properly
    // check blobkey
    // know file size
    // don't throw
    // avoid void
    
//    if (blobKey != null) {
//      
//    }
    
    FileOutputStream stream = new FileOutputStream(file);
        
    long blockSize = 1024 * 512;
    long inxStart = 0;
    long inxEnd = blockSize;
    boolean flag = false;

    do {
      try {
        byte[] b = blobstoreService.fetchData(blobKey, inxStart, inxEnd);
        
        stream.write(b);

        if (b.length < blockSize)
          flag = true;

        inxStart = inxEnd + 1;
        inxEnd += blockSize + 1;

      } catch (Exception e) {
        flag = true;
      }

    } while (!flag);
    
    stream.close();
  }
}
