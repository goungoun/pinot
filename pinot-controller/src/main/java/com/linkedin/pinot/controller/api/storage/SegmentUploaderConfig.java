package com.linkedin.pinot.controller.api.storage;

import com.linkedin.pinot.controller.helix.core.PinotHelixResourceManager;
import javax.ws.rs.core.HttpHeaders;
import org.glassfish.grizzly.http.server.Request;


public class SegmentUploaderConfig {
  private final HttpHeaders _headers;
  private final boolean _enableParallelPushProtection;
  private final Request _request;
  private final PinotStorageType _pinotStorageType;
  private final String _pinotStorageDir;
  private final PinotHelixResourceManager _pinotHelixResourceManager;


  private SegmentUploaderConfig(HttpHeaders headers, boolean enableParallelPushProtection, Request request,
      PinotStorageType pinotStorageType, String pinotStorageDir, PinotHelixResourceManager pinotHelixResourceManager) {
    _headers = headers;
    _enableParallelPushProtection = enableParallelPushProtection;
    _request = request;
    _pinotStorageType = pinotStorageType;
    _pinotStorageDir = pinotStorageDir;
    _pinotHelixResourceManager = pinotHelixResourceManager;
  }

  public HttpHeaders getHeaders() {
    return _headers;
  }

  public boolean getEnableParallelPushProtection() {
    return _enableParallelPushProtection;
  }

  public Request getRequest() {
    return _request;
  }

  public PinotStorageType getPinotStorageType() {
    return _pinotStorageType;
  }

  public String getPinotStorageDir() {
    return _pinotStorageDir;
  }

  public PinotHelixResourceManager getPinotHelixResourceManager() {
    return _pinotHelixResourceManager;
  }

  public static class SegmentUploaderConfigBuilder {
    private HttpHeaders _headers;
    private boolean _enableParallelPushProtection;
    private Request _request;
    private PinotStorageType _pinotStorageType;
    private String _pinotStorageDir;
    private PinotHelixResourceManager _pinotHelixResourceManager;

    private SegmentUploaderConfigBuilder() {
    }

    public SegmentUploaderConfigBuilder setHeaders(HttpHeaders headers) {
      _headers = headers;
      return this;
    }

    public SegmentUploaderConfigBuilder setEnableParallelPushProtection(boolean enableParallelPushProtection) {
      _enableParallelPushProtection = enableParallelPushProtection;
      return this;
    }

    public SegmentUploaderConfigBuilder setRequest(Request request) {
      _request = request;
      return this;
    }

    public SegmentUploaderConfigBuilder setPinotStorageType(PinotStorageType pinotStorageType) {
      _pinotStorageType = pinotStorageType;
      return this;
    }

    public SegmentUploaderConfigBuilder setPinotStorageDir(String pinotStorageDir) {
      _pinotStorageDir = pinotStorageDir;
      return this;
    }

    public SegmentUploaderConfigBuilder setPinotHelixResourceManager(PinotHelixResourceManager pinotHelixResourceManager) {
      _pinotHelixResourceManager = pinotHelixResourceManager;
      return this;
    }

    public SegmentUploaderConfig build() {
      return new SegmentUploaderConfig(_headers, _enableParallelPushProtection, _request, _pinotStorageType, _pinotStorageDir, _pinotHelixResourceManager);
    }
  }
}
