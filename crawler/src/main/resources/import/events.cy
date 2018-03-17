////////////////////////////////////
// DATASET: events (1)
////////////////////////////////////

CALL apoc.load.json('https://acel.lu/api/v1/events') YIELD value as event
UNWIND event.location as location
CREATE (e:Event {id: event.id , description: event.description, source: 'acel.lu' })
MERGE (l:Locality { name: location.locality })
CREATE (e)-[:TAKES_PLACE_IN]->(l);


////////////////////////////////////
// DATASET: events (2)
////////////////////////////////////

CALL apoc.load.json('https://public.opendatasoft.com/explore/dataset/evenements-publics-cibul/download/?format=json&disjunctive.tags=true&disjunctive.placename=true&disjunctive.city=true&q=luxembourg&refine.department=Luxembourg&timezone=Europe/Berlin') YIELD value as event create(e:Event{recordid: event.fields.recordid, description: event.fields.description, start: event.fields.date_start, end: event.fields.date_end, coordinates: event.geometry.coordinates} );

