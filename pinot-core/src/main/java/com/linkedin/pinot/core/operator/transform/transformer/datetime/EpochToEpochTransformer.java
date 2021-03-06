/**
 * Copyright (C) 2014-2016 LinkedIn Corp. (pinot-core@linkedin.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.linkedin.pinot.core.operator.transform.transformer.datetime;

import com.linkedin.pinot.common.data.DateTimeFormatSpec;
import com.linkedin.pinot.common.data.DateTimeGranularitySpec;
import javax.annotation.Nonnull;


/**
 * Date time transformer to transform and bucket date time values from an epoch format to another epoch format.
 */
public class EpochToEpochTransformer extends BaseDateTimeTransformer<long[], long[]> {

  public EpochToEpochTransformer(DateTimeFormatSpec inputFormat, DateTimeFormatSpec outputFormat,
      DateTimeGranularitySpec outputGranularity) {
    super(inputFormat, outputFormat, outputGranularity);
  }

  @Override
  public void transform(@Nonnull long[] input, @Nonnull long[] output, int length) {
    for (int i = 0; i < length; i++) {
      output[i] = transformMillisToEpoch(transformToOutputGranularity(transformEpochToMillis(input[i])));
    }
  }
}
