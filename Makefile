
build:
	docker build -f Dockerfile -t demo/oracle-java:8 .

run:
	docker run -p 1099:1099 -p 9000-9100:9000-9100 demo/oracle-java:8 java -jar ./server_jar/TerningSpillet_Snyd.jar


runlol:
	docker run -p 1099:1099 -p 9000-9100:9000-9100 demo/oracle-java:8 java -jar ./server_jar/snyd_server/TerningSpillet_Snyd_server.jar
