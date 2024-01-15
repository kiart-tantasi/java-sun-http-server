# How to run

1. Start docker compose and wait for mysql to be ready
```
docker-compose up
```

2. Compile to .class files
```
javac app/App.java
```

3. Start java app
```
java app.App
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

1. In `./app/handlers/UserHandler.java`, Change `localhost:3306` to `mysql:3306`

2. Build docker image
```
docker build -t app .
```

3. Run docker-compose up to start MySQL and wait until it is ready
```
docker-compose up
```

4. Run container
```
docker run -p 8080:8080 --network=<docker-compose-network-name> app
```

5. Check result
```
curl http://localhost:8080/user
```
