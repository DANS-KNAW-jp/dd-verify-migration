/*
 * Copyright (C) 2021 DANS - Data Archiving and Networked Services (info@dans.knaw.nl)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.knaw.dans.filemigration.core;

import io.dropwizard.hibernate.UnitOfWork;
import nl.knaw.dans.filemigration.api.EasyFile;
import nl.knaw.dans.filemigration.api.Expected;
import nl.knaw.dans.filemigration.db.EasyFileDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EasyFileLoader {
  private static final Logger log = LoggerFactory.getLogger(EasyFileLoader.class);

  private final EasyFileDAO dao;

  public EasyFileLoader(EasyFileDAO dao) {
    this.dao = dao;
  }

  public void loadFromCsv(FedoraToBagCsv csv) {
    log.trace(csv.toString());
    if (csv.getComment().contains("OK"))
      loadFromDatasetId(csv.getDatasetId(),csv.getDoi());
    else log.warn("skipped {}", csv);
  }

  @UnitOfWork
  public void loadFromDatasetId(String datasetId, String doi) {
    log.trace("{} {}", datasetId , doi);
    for (EasyFile ef : dao.findByDatasetId(datasetId)) {
      log.trace("EasyFile = {}" , ef);
      Expected expected = new Expected();
      expected.setEasy_file_id(ef.getDataset_sid());
      expected.setFs_rdb_path(ef.getPath());
      expected.setSha1checksum(ef.getSha1checksum());
      expected.setDoi(doi);
      log.trace("Expected = {}", expected);
    }
  }
}
