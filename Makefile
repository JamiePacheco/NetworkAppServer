neo4j-server:
	docker run --restart always --publish=7474:7474 --publish=7687:7687 --env NEO4J_AUTH=neo4j/password  neo4j

# run neo4j:
neo4j-server-persist:
	mkdir ./src/main/resources/data \
	docker run --restart always \
		--publish=7474:7474 \
		--publish=7687:7687 \
		--env NEO4J_AUTH=neo4j/password \
		--volume ./src/main/resources/data \
		neo4j