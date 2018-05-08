package com.linkedin.pinot.controller.api.storage;

import java.io.File;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;


public class StorageLocationConfig extends PropertiesConfiguration {
  private static final String PINOT_STORAGE_DIR = "pinot.storage.dir";
  private static final String PINOT_STORAGE_TYPE = "pinot.storage.type";
  private static final String PINOT_ENABLE_RETENTION = "pinot.enable.retention";
  private static final String PINOT_ENABLE_SAFE_UPLOAD = "pinot.enable.safe.upload";

  private static final String DEFAULT_PINOT_STORAGE_DIR = "/export";

  public StorageLocationConfig() {
    super();
  }

  public StorageLocationConfig(File file) throws ConfigurationException {
    super(file);
  }

  public String getPinotStorageDir() {
    if (containsKey(PINOT_STORAGE_DIR) && ((String) getProperty(PINOT_STORAGE_DIR)).length() > 0) {
      return ((String) getProperty(PINOT_STORAGE_DIR));
    }
    return DEFAULT_PINOT_STORAGE_DIR;
  }

  public void setPinotStorageDir(String pinotStorageDir) {
    setProperty(PINOT_STORAGE_DIR, pinotStorageDir);
  }

}
