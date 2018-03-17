///////////////////////////
// Find the nearest parking
///////////////////////////

WITH point({latitude: 49.600690970137855, longitude: 6.113794412913669}) as my_position
MATCH (n)
WHERE n:Parking:CoveredParking or n:Parking:ParkingSurface
WITH n, distance(point({latitude: n.coordinates[0], longitude: n.coordinates[1]}), my_position)  AS distance
ORDER BY distance
LIMIT 5
RETURN n