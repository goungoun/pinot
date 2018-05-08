package com.linkedin.pinot.controller.api.storage;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;


public class LocalPinotFS implements PinotFS {
  public static String _storageDir;
  public LocalPinotFS(String storageDir) {
    _storageDir = storageDir;
  }
  public boolean delete(URI uri) throws Exception {
    File file = new File(_storageDir, uri.getPath());
    return file.delete();
  }
  public boolean move(URI srcUri, URI dstUri) throws Exception {
    // TODO: Make sure parent directories are created
    Files.move(Paths.get(_storageDir, srcUri.toString()), Paths.get(_storageDir, dstUri.toString()));
    return true;
  }
  public boolean copy(URI srcUri, URI dstUri) throws Exception {
    Files.copy(Paths.get(_storageDir, srcUri.toString()), Paths.get(_storageDir, dstUri.toString()));
    return true;
  }
  public boolean exists(URI uri) {
    File file = new File(_storageDir, uri.getPath());
    return file.exists();
  }
  public long length(URI uri) {
    File file = new File(_storageDir, uri.getPath());
    return file.length();
  }
  public String[] listFiles(URI uri) {
    File file = new File(_storageDir, uri.getPath());
    return file.list();
  }
}
