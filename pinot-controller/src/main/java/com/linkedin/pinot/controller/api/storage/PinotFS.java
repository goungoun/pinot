package com.linkedin.pinot.controller.api.storage;

import java.net.URI;


/**
 *
 */
public interface PinotFS {
//  void init(StorageLocationConfig config);
  boolean delete(URI uri) throws Exception;
  boolean move(URI srcUri, URI dstUri) throws Exception;
  boolean copy(URI srcUri, URI dstUri) throws Exception;
  boolean exists(URI uri);
  long length(URI uri);
  String[] listFiles(URI uri);
}
