docker run --name accounts -d -p 8080:8080 totakai/accounts:1.0 
docker run -p 3306:3306 --name accountsdb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=accountsdb -d mysql:late