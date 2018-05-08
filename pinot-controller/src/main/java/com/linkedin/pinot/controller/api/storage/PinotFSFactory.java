//package com.linkedin.pinot.controller.api.storage;
//
//import org.apache.commons.configuration.Configuration;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//
//public abstract class PinotFSFactory {
//  public static final Logger LOGGER = LoggerFactory.getLogger(PinotFSFactory.class);
//
//  public abstract void init(Configuration configuration);
//
//  public abstract PinotFS create();
//
//  public static PinotFSFactory loadFactory(PinotStorageType pinotStorageType) {
//    PinotFSFactory pinotFSFactory;
//
//    try {
//      LOGGER.info("Instantiating Access control factory class {}", accessControlFactoryClassName);
//      accessControlFactory =  (AccessControlFactory) Class.forName(accessControlFactoryClassName).newInstance();
//      LOGGER.info("Initializing Access control factory class {}", accessControlFactoryClassName);
//      accessControlFactory.init(configuration);
//      return accessControlFactory;
//    } catch (Exception e) {
//      throw new RuntimeException(e);
//    }
//  }
//}
