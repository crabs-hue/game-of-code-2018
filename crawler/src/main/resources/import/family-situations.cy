////////////////////////////////////
// DATASET: family_situation
////////////////////////////////////

LOAD CSV WITH HEADERS FROM 'https://data.public.lu/en/datasets/r/148e2f20-19ac-4161-82a7-067178a24aa5' AS csvLine FIELDTERMINATOR ';'
MERGE (l:Locality { name: csvLine.COMMUNE_NOM })
MERGE (s1:FamilySituation {code: 'CELIBATAIRES'})
MERGE (s2:FamilySituation {code: 'MARIEES'})
MERGE (s3:FamilySituation {code: 'VEUVES'})
MERGE (s4:FamilySituation {code: 'DIVORCEES'})
MERGE (s5:FamilySituation {code: 'PACSEES'})
MERGE (s6:FamilySituation {code: 'SEPAREES'})
MERGE (s7:FamilySituation {code: 'SITUATIONS_INCONNUES'})
CREATE (l)-[:HAS_SITUATION { total: toInteger(csvLine.PERSONNES_CELIBATAIRES) }]->(s1)
CREATE (l)-[:HAS_SITUATION { total: toInteger(csvLine.PERSONNES_MARIEES) }]->(s2)
CREATE (l)-[:HAS_SITUATION { total: toInteger(csvLine.PERSONNES_VEUVES) }]->(s3)
CREATE (l)-[:HAS_SITUATION { total: toInteger(csvLine.PERSONNES_DIVORCEES) }]->(s4)
CREATE (l)-[:HAS_SITUATION { total: toInteger(csvLine.PERSONNES_PACSEES) }]->(s5)
CREATE (l)-[:HAS_SITUATION { total: toInteger(csvLine.PERSONNES_SEPAREES) }]->(s6)
CREATE (l)-[:HAS_SITUATION { total: toInteger(csvLine.PERSONNES_SITUATIONS_INCONNUES) }]->(s7);
