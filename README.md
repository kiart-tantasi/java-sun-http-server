# How to run

1. start docker compose and wait for mysql to be ready
```
docker-compose up
```

2. run Java app
```
java -cp .:./libs/* app/*
```

3. Try
```
curl http://localhost:8080/healthz
# and
curl http://localhost:8080/user
```

# Check result

```
docker exec -it <mysql-container-id> mysql -u root -p mydb
# enter password as set in docker-compose.yaml and then
select * from test;
```

# Clean up

```
docker-compose down --volumes
```

# Production

1. Compile to .class file (bytecode)
```
javac app/App.java
```

2. Compile .class files to .jar file
```
jar -cfm App.jar manifest.txt
```

3. Start app
```
java -jar App.jar
```
