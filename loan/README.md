Command  to build image using buildpack:
[]: # mvn spring-boot:build-image

docker run --name loans -d -p 8090:8090 totakai/loans:1.0 