////////////////////////////////////
// DATASET: rnrpp_nationalite_commune
////////////////////////////////////

CREATE INDEX ON :Nationality(code);

LOAD CSV WITH HEADERS FROM "https://download.data.public.lu/resources/nombre-de-ressortissants-par-nationalite-et-par-commune-number-of-citizens-per-nationality-and-municipality/20180102-080809/rnrpp_nationalite_commune.csv" as csvLine
MERGE (l:Locality { name: csvLine.COMMUNE_NOM })
MERGE (n:Nationality { code: csvLine.NATIONALITE_ISO3 })
CREATE (n)-[:LIVE_IN { total: toInteger(csvLine.NOMBRE_TOTAL) }]->(l);


////////////////////////////////////
// Sample query
////////////////////////////////////

MATCH (n:Nationality{code:'XXK'}) RETURN n;
