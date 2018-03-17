///////////////////////////
// Find the nearest events
///////////////////////////

WITH point({latitude: 49.659418, longitude: 6.242811}) as my_position
MATCH (hn:HouseNumber)
WITH hn, distance(point({latitude: toFloat(hn.lat), longitude: toFloat(hn.lon)}), my_position)  AS distance
ORDER BY distance
LIMIT 1
MATCH (hn)-[*2..2]-(l:Locality)
WITH l
LIMIT 1
RETURN l