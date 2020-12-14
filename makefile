dev:
	mvn clean
	mvn install -DskipTests=true
	docker build -f Dockerfile -t roleservice .
	docker-compose up