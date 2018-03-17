////////////////////////////////////
// DATASET: postal_services
////////////////////////////////////

CREATE INDEX ON :PostalService(id);

LOAD CSV WITH HEADERS FROM 'https://data.public.lu/fr/datasets/r/7be38c21-4680-42ff-9adc-779898784f44' AS csvLine FIELDTERMINATOR ';'
MERGE (l:PostalCode {code: substring(csvLine.zip_code, 2)})
MERGE (p:PostalService {id: csvLine.id})
ON CREATE SET p.type = csvLine.code, p.lat = csvLine.gps_lat_coord, p.long = csvLine.gps_long_coord
MERGE (p)-[:POST_SERVICE_IN]->(l);
