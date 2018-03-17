////////////////////////////////////
// DATASET: vdl
////////////////////////////////////

CALL apoc.load.json('https://opendata.vdl.lu/odaweb/index.jsp?cat=4ea917f2155670ea92f17bf4') YIELD value as parking UNWIND parking.features as features CREATE(p:Parking:CoveredParking {name: features.properties.name, coordinates: features.geometry.coordinates, bbox: features.bbox});

CALL apoc.load.json('https://opendata.vdl.lu/odaweb/index.jsp?cat=4ea917f2155670ea92f17bf3') YIELD value as parking UNWIND parking.features as features CREATE(p:Parking:ParkingSurface {name: features.properties.name, coordinates: features.geometry.coordinates, bbox: features.bbox});

CALL apoc.load.json('https://opendata.vdl.lu/odaweb/index.jsp?cat=4ea917f2155670ea92f17aef') YIELD value as parking UNWIND parking.features as features CREATE(p:Parking:MotorcycleParking {name: features.properties.name, coordinates: features.geometry.coordinates, bbox: features.bbox});

CALL apoc.load.json('https://opendata.vdl.lu/odaweb/index.jsp?cat=4ea917f2155670ea92f17bf2') YIELD value as parking UNWIND parking.features as features CREATE(p:Parking:DisabledParking {name: features.properties.name, coordinates: features.geometry.coordinates, bbox: features.bbox});
