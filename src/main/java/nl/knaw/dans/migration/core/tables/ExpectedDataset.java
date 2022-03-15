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
package nl.knaw.dans.migration.core.tables;

import nl.knaw.dans.migration.core.AccessCategory;
import nl.knaw.dans.migration.core.FileRights;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(ExpectedDatasetKey.class)
@Table(name = "expected_datasets")
public class ExpectedDataset {
    // https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html#schema-generation

    // most lengths from easy-dtap/provisioning/roles/easy-fs-rdb/templates/create-easy-db-tables.sql
    // doi length as in dd-dtap/shared-code/dataverse/scripts/database/create/create_v*.sql

    @Id
    @Column(length = 255)
    private String doi;

    @Column(name="access_category")
    private String accessCategory;

    @Nullable
    @Column(name="embargo_date")
    private String embargoDate;

    @Column(name = "depositor")
    private String depositor;

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getAccessCategory() {
        return accessCategory;
    }

    public void setAccessCategory(AccessCategory accessCategory) {
        this.accessCategory = accessCategory.toString();
    }

    @Nullable
    public String getEmbargoDate() {
        return embargoDate;
    }

    public void setEmbargoDate(FileRights fileRights) {
        // the logic for a date in the future is in the hands of fileRights
        this.embargoDate = fileRights.getEmbargoDate();
    }

    public String getDepositor() {
        return depositor;
    }

    public void setDepositor(String depositor) {
        this.depositor = depositor;
    }

    @Override
    public String toString() {
        return "ExpectedDataset{" +
                "doi='" + doi + '\'' +
                ", accessCategory='" + accessCategory + '\'' +
                ", embargoDate='" + embargoDate + '\'' +
                ", depositor='" + depositor + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpectedDataset that = (ExpectedDataset) o;
        return Objects.equals(doi, that.doi) && Objects.equals(accessCategory, that.accessCategory) && Objects.equals(embargoDate, that.embargoDate) && Objects.equals(depositor, that.depositor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doi, accessCategory, embargoDate, depositor);
    }
}