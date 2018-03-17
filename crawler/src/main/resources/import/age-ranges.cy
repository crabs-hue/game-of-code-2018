////////////////////////////////////
// DATASET: rnrpp_pyramide_age_commune
////////////////////////////////////

CREATE INDEX ON :AgeRange(code);

LOAD CSV WITH HEADERS FROM "https://download.data.public.lu/resources/pyramide-dage-par-commune-population-age-pyramid-per-municipality/20180102-081134/rnrpp_pyramide_age_commune.csv" as csvLine
MERGE (l:Locality { name: csvLine.COMMUNE_NOM })
MERGE (s1:AgeRange { code: 'AGE00_05', inner: 0, outer: 5 })
MERGE (s2:AgeRange { code: 'AGE05_10', inner: 5, outer: 10 })
MERGE (s3:AgeRange { code: 'AGE10_15', inner: 10, outer: 15 })
MERGE (s4:AgeRange { code: 'AGE15_20', inner: 15, outer: 20 })
MERGE (s5:AgeRange { code: 'AGE20_25', inner: 20, outer: 25 })
MERGE (s6:AgeRange { code: 'AGE25_30', inner: 25, outer: 30 })
MERGE (s7:AgeRange { code: 'AGE30_35', inner: 30, outer: 35 })
MERGE (s8:AgeRange { code: 'AGE35_40', inner: 35, outer: 40 })
MERGE (s9:AgeRange { code: 'AGE40_45', inner: 40, outer: 45 })
MERGE (s10:AgeRange { code: 'AGE45_50', inner: 45, outer: 50 })
MERGE (s11:AgeRange { code: 'AGE50_55', inner: 50, outer: 55 })
MERGE (s12:AgeRange { code: 'AGE55_60', inner: 55, outer: 60 })
MERGE (s13:AgeRange { code: 'AGE60_65', inner: 60, outer: 65 })
MERGE (s14:AgeRange { code: 'AGE65_70', inner: 65, outer: 70 })
MERGE (s15:AgeRange { code: 'AGE70_75', inner: 70, outer: 75 })
MERGE (s16:AgeRange { code: 'AGE75_80', inner: 75, outer: 80 })
MERGE (s17:AgeRange { code: 'AGE80_85', inner: 80, outer: 85 })
MERGE (s18:AgeRange { code: 'AGE85_90', inner: 85, outer: 90 })
MERGE (s19:AgeRange { code: 'AGE90_95', inner: 90, outer: 95 })
MERGE (s20:AgeRange { code: 'AGE95_100', inner: 95, outer: 100 })
MERGE (s21:AgeRange { code: 'AGE100_', inner: 100 })
CREATE (l)-[:HAS_AGE { total: toInteger(csvLine.AGE00_05), sex: csvLine.SEXE }]->(s1)
CREATE (l)-[:HAS_AGE { total: toInteger(csvLine.AGE05_10), sex: csvLine.SEXE }]->(s2)
CREATE (l)-[:HAS_AGE { total: toInteger(csvLine.AGE10_15), sex: csvLine.SEXE }]->(s3)
CREATE (l)-[:HAS_AGE { total: toInteger(csvLine.AGE15_20), sex: csvLine.SEXE }]->(s4)
CREATE (l)-[:HAS_AGE { total: toInteger(csvLine.AGE20_25), sex: csvLine.SEXE }]->(s5)
CREATE (l)-[:HAS_AGE { total: toInteger(csvLine.AGE25_30), sex: csvLine.SEXE }]->(s6)
CREATE (l)-[:HAS_AGE { total: toInteger(csvLine.AGE30_35), sex: csvLine.SEXE }]->(s7)
CREATE (l)-[:HAS_AGE { total: toInteger(csvLine.AGE35_40), sex: csvLine.SEXE }]->(s8)
CREATE (l)-[:HAS_AGE { total: toInteger(csvLine.AGE40_45), sex: csvLine.SEXE }]->(s9)
CREATE (l)-[:HAS_AGE { total: toInteger(csvLine.AGE45_50), sex: csvLine.SEXE }]->(s10)
CREATE (l)-[:HAS_AGE { total: toInteger(csvLine.AGE50_55), sex: csvLine.SEXE }]->(s11)
CREATE (l)-[:HAS_AGE { total: toInteger(csvLine.AGE55_60), sex: csvLine.SEXE }]->(s12)
CREATE (l)-[:HAS_AGE { total: toInteger(csvLine.AGE60_65), sex: csvLine.SEXE }]->(s13)
CREATE (l)-[:HAS_AGE { total: toInteger(csvLine.AGE65_70), sex: csvLine.SEXE }]->(s14)
CREATE (l)-[:HAS_AGE { total: toInteger(csvLine.AGE70_75), sex: csvLine.SEXE }]->(s15)
CREATE (l)-[:HAS_AGE { total: toInteger(csvLine.AGE75_80), sex: csvLine.SEXE }]->(s16)
CREATE (l)-[:HAS_AGE { total: toInteger(csvLine.AGE80_85), sex: csvLine.SEXE }]->(s17)
CREATE (l)-[:HAS_AGE { total: toInteger(csvLine.AGE85_90), sex: csvLine.SEXE }]->(s18)
CREATE (l)-[:HAS_AGE { total: toInteger(csvLine.AGE90_95), sex: csvLine.SEXE }]->(s19)
CREATE (l)-[:HAS_AGE { total: toInteger(csvLine.AGE95_100), sex: csvLine.SEXE }]->(s20)
CREATE (l)-[:HAS_AGE { total: toInteger(csvLine.AGE100_), sex: csvLine.SEXE }]->(s21);


////////////////////////////////////
// Sample query
////////////////////////////////////

MATCH (n:AgeRange)-[r:HAS_AGE{sex:'F'}]-(l:Locality) WHERE n.inner > 19 AND n.outer < 31 RETURN n,r,l ORDER BY toInteger(r.total) DESC LIMIT 5;
