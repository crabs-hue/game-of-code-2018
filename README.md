# Project Code Undefined

## Backend

Sources are located under the `backend` module.

The main application class is:
`lu.arhs.cube.gameofcode.projectcodeundefined.ProjectCodeUndefinedApp`

Start it as usual for a Spring Boot application.

## API

The Api is accessible via a GraphQL client such "Altair GraphQL client" which can be installed on Firefox or google chrome as an extension.

URL: http://ec2-52-211-57-228.eu-west-1.compute.amazonaws.com:8080/graphql/
Username: neo4j
Password: password

Example GraphQL query:

```
{
 Route(name: "RE7400"){
   id
  hasSpotpoint {
    name
  }
 }
}
```

Example ouput:
```
{
  "data": {
    "Route": [
      {
        "id": "2:C82---:RE7400",
        "hasSpotpoint": [
          {
            "name": "Luxembourg, Gare Centrale"
          },
          {
            "name": "Hollerich, Gare"
          },
          {
            "name": "Dippach-Reckange, Gare"
          },
          {
            "name": "Bascharage/Sanem, Gare"
          },
          {
            "name": "Pétange, Gare"
          },
          {
            "name": "Rodange, Gare"
          }
        ]
      }
    ]
  },
  "extensions": {
    "type": "READ_ONLY"
  }
}
```

## NEO4J

An instance of NEO4J is deployed at bolt://neo4j@ec2-52-211-57-228.eu-west-1.compute.amazonaws.com:7687.

## Queries
The neo4j cipher queries are located at https://github.com/crabs-hue/game-of-code-2018/tree/master/crawler/src/main/resources.
