Levantar un contenedor con mysql
Crea un volume
docker volume create data-mysql
Crea el contenedor
docker run -d -p 3307:3306 --name mysql8 --network spring -e MYSQL_ROOT_PASSWORD=sasa -e MYSQL_DATABASE=db_pisos -v data-mysql:/var/lib/mysql --restart=always mysql:8
