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
package nl.knaw.dans.filemigration.db;

import io.dropwizard.hibernate.AbstractDAO;
import nl.knaw.dans.filemigration.api.EasyFile;
import org.hibernate.SessionFactory;

import java.util.List;

public class EasyFileDAO extends AbstractDAO<EasyFile> {

  public EasyFileDAO(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<EasyFile> findByDatasetId(String id) {
    return list(namedTypedQuery(EasyFile.FIND_BY_DATASET_ID).setParameter(EasyFile.DATASET_ID, id));
  }
}
