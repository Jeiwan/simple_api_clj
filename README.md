# simple_api

FIXME

## Usage

### Run the application locally

`lein ring server`

### Migrations
```
lein run -m simple_api.db/migrate
lein with-profile test run -m simple_api.db/migrate
```

### Run the tests

`lein test`

### Packaging and running as standalone jar

```
lein do clean, ring uberjar
java -jar target/server.jar
```

### Packaging as war

`lein ring uberwar`

## License

Copyright ©  FIXME
