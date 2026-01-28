Command  to build image using google jib:
mvn compile jib:dockerBuild


docker run --name cards -d -p 9000:9000 totakai/cards:1.0 