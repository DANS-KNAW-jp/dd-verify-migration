package nl.knaw.dans.filemigration.core;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FedoraToBagCsv {

  private final String datasetId;
  private final String doi;
  private final String comment;
  private final String type;
  private final CSVRecord r;

  private static final String datasetIdColumn = "easyDatasetId";
  private static final String doiColumn = "doi";
  private static final String commentColumn = "comment"; // "OK", "not strict OK", "OK; no payload...", "FAILED..."
  private static final String typeColumn = "transformationType"; // simple, thematische-collectie,original-versioned, fedora-versioned

  public FedoraToBagCsv(CSVRecord r) {
    datasetId = r.get(datasetIdColumn);
    doi = r.get(doiColumn);
    comment = r.get(commentColumn);
    type = r.get(typeColumn);
    this.r = r;
  }

  @Override
  public String toString() {
    // TODO generated by IntelliJ but do we want this?
    return "FedoraToBagCsv{" + "datasetId='" + datasetId + '\'' + ", doi='" + doi + '\'' + ", comment='" + comment + '\'' + ", type='" + type + '\'' + ", r=" + r + '}';
  }

  // see https://github.com/DANS-KNAW/easy-fedora-to-bag/blob/8ef3a0bad/src/main/scala/nl/knaw/dans/easy/fedoratobag/CsvRecord.scala#L42-L46
  private static final CSVFormat csvFormat = CSVFormat
      .RFC4180
      .withHeader(datasetIdColumn, "uuid1", "uuid2", doiColumn, "depositor", typeColumn, commentColumn)
      .withDelimiter(',')
      .withFirstRecordAsHeader()
      .withRecordSeparator('\n')
      .withAutoFlush(true);

  static public CSVParser parse(File file) throws IOException {
    return CSVParser.parse(file, StandardCharsets.UTF_8, csvFormat);
  }

  public String getType() {
    return type;
  }

  public String getComment() {
    return comment;
  }

  public String getDoi() {
    return doi;
  }

  public String getDatasetId() {
    return datasetId;
  }
}
