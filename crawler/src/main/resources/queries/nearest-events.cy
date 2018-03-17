///////////////////////////
// Find the nearest events
///////////////////////////

WITH point({latitude: 49.600690970137855, longitude: 6.113794412913669}) as my_position
MATCH (event:Event)
WITH event, distance(point({latitude: event.coordinates[0], longitude: event.coordinates[1]}), my_position)  AS distance
ORDER BY distance
LIMIT 5
RETURN event
