package com.linkedin.pinot.controller.api.storage;

import com.linkedin.pinot.common.config.TableNameBuilder;
import com.linkedin.pinot.common.metadata.segment.OfflineSegmentZKMetadata;
import com.linkedin.pinot.common.segment.SegmentMetadata;
import com.linkedin.pinot.common.utils.CommonConstants;
import com.linkedin.pinot.controller.api.resources.SuccessResponse;
import com.linkedin.pinot.controller.helix.core.PinotHelixResourceManager;
import java.net.URI;
import javax.ws.rs.core.HttpHeaders;
import org.apache.helix.ZNRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class contains the operations for each storage type.
 */
public class PinotSegmentUploadUtils {
  private static final Logger LOGGER = LoggerFactory.getLogger(PinotSegmentUploadUtils.class);


//  /**
//   * Uploads segment and metadata to Pinot storage. Note that this will not mark segments online.
//   * @param file
//   * @param uploadLocation
//   * @return
//   */
//  SuccessResponse uploadSegmentDataAndMetadata(File file, String uploadLocation);
//
//  /**
//   * Makes segment available for serving.
//   * @param file
//   * @return
//   */
//  SuccessResponse sendSegmentMetadataForIdealState(File file);
//
//  /**
//   * Marks segment as unavailable for serving. Note that the data still remains in storage.
//   * @param file
//   * @return
//   */
//  SuccessResponse deleteSegment(File file);
//
//  /**
//   * Permanently removes all versions of the physical copy of the partition.
//   * @param file
//   * @return
//   */
//  SuccessResponse removeSegmentPermanently(File file);

  public SuccessResponse push(SegmentMetadata segmentMetadata, URI segmentUri, SegmentUploaderConfig segmentUploaderConfig) {
    String storageDirectory = segmentUploaderConfig.getPinotStorageDir();

    HttpHeaders headers = segmentUploaderConfig.getHeaders();
    if (headers != null) {
      // TODO: Add these headers into open source hadoop jobs
      LOGGER.info("HTTP Header {} is {}", CommonConstants.Controller.SEGMENT_NAME_HTTP_HEADER,
          headers.getRequestHeader(CommonConstants.Controller.SEGMENT_NAME_HTTP_HEADER));
      LOGGER.info("HTTP Header {} is {}", CommonConstants.Controller.TABLE_NAME_HTTP_HEADER,
          headers.getRequestHeader(CommonConstants.Controller.TABLE_NAME_HTTP_HEADER));
    }

    String tableName = segmentMetadata.getTableName();
    String offlineTableName = TableNameBuilder.OFFLINE.tableNameWithType(tableName);

    String segmentName = segmentMetadata.getName();

    // TODO: Add factory initiation
    PinotFS pinotFS = new LocalPinotFS(storageDirectory);
    URI dstUri;
    try {
      // TODO : Get highest version
      dstUri = new URI(storageDirectory + "/" + segmentMetadata.getTableName() + "/" + segmentMetadata.getName());
      pinotFS.copy(segmentUri, dstUri);

    } catch (Exception e) {
      throw new RuntimeException("Could not construct destination URI");
    }

    // check that offline table config exists
    // Check quota
    // check that time range of segment is valid
    // IF CHECKS FAIL, DELETE FILE

    PinotHelixResourceManager pinotHelixResourceManager = segmentUploaderConfig.getPinotHelixResourceManager();
    ZNRecord znRecord = pinotHelixResourceManager.getSegmentMetadataZnRecord(offlineTableName, segmentName);

    // Brand new segment, not refresh, directly add the segment
    if (znRecord == null) {
      pinotHelixResourceManager.addNewSegment(segmentMetadata, dstUri.toString());
      // return success
    }

    // Segment already exists, refresh if necessary
    OfflineSegmentZKMetadata existingSegmentZKMetadata = new OfflineSegmentZKMetadata(znRecord);
    long existingCrc = existingSegmentZKMetadata.getCrc();

    // Check crc match here
    // enale parallel push protection check
    // lock segment in zk

    pinotHelixResourceManager.refreshSegment(segmentMetadata, existingSegmentZKMetadata, dstUri.toString());
    if (!pinotHelixResourceManager.updateZkMetadata(existingSegmentZKMetadata)) {
      LOGGER.error("Failed to update ZK metadata for segment: {} of table: {}", segmentName, offlineTableName);
    }

    return new SuccessResponse("Segment uploaded");
  }
}
