////////////////////////////////////
// DATASET: rnrpp_code_postal
////////////////////////////////////

LOAD CSV WITH HEADERS FROM "https://download.data.public.lu/resources/population-par-code-postal-population-per-postal-code/20180102-075950/rnrpp_code_postal.csv" as csvLine
MERGE (pc:PostalCode { code: csvLine.CODE_POSTAL })
ON CREATE SET pc.num_habitants = toInteger(csvLine.NOMBRE_HABITANTS)
ON MATCH SET pc.num_habitants = toInteger(csvLine.NOMBRE_HABITANTS);

////////////////////////////////////
// Sample query
///////////////////////////////////

MATCH (n:Locality{name:'Luxembourg'})-[:IS_IN]-(:Address)-[:BELONGS_TO]-(pc:PostalCode) RETURN SUM(pc.num_habitants) AS total;
