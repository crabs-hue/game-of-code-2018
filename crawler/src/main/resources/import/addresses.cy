////////////////////////////////////
// DATASET: addresses
////////////////////////////////////

CREATE INDEX ON :Locality(name);

LOAD CSV WITH HEADERS FROM "https://download.data.public.lu/resources/adresses-georeferencees-bd-adresses/20180312-053240/addresses.csv" as csvLine FIELDTERMINATOR ';'
MERGE (l:Locality { name: csvLine.localite });

CREATE INDEX ON :Address(road, postal_code);
CREATE INDEX ON :PostalCode(code);

LOAD CSV WITH HEADERS FROM "https://download.data.public.lu/resources/adresses-georeferencees-bd-adresses/20180312-053240/addresses.csv" as csvLine FIELDTERMINATOR ';'
MERGE (l:Locality { name: csvLine.localite })
MERGE (pc:PostalCode { code: csvLine.code_postal })
MERGE (a:Address { road: csvLine.rue, postal_code: csvLine.code_postal })
MERGE (a)-[:IS_IN]->(l)
MERGE (a)-[:BELONGS_TO]->(pc);

LOAD CSV WITH HEADERS FROM "https://download.data.public.lu/resources/adresses-georeferencees-bd-adresses/20180312-053240/addresses.csv" as csvLine FIELDTERMINATOR ';'
MERGE (a:Address { road: csvLine.rue, postal_code: csvLine.code_postal })
CREATE (n:HouseNumber { id: csvLine.numero, lat: csvLine.lat_wgs84, lon: csvLine.lon_wgs84 })
MERGE (n)-[:LOCATED_IN]->(a);


////////////////////////////////////
// Sample query
////////////////////////////////////

MATCH (a:Address)-[r:IS_IN]->(:Locality) WITH a, COUNT(r) AS c WHERE c > 1 RETURN a;