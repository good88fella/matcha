# matcha

< DATABASE

#### Dockerfile
Используем стандартный образ postgres и копируем два скрипта в стандартную папку docker-entrypoint-initdb.d.<br>
Скрипты выполняться по порядку, исходя из имени скрипта, при создании контейнера.<br>
00_init.sh - создает пользователя и базу данных.<br>
01_create_tables.sh - создает таблицы.<br>
#### Makefile
build: создаем докер образ с именем 21school/matcha<br>
run: создаем докер контейнер с именем matcha<br>
#### Build image and container
make build<br>
make run<br>
#### Delete image and container
./del.sh<br>
#### Start and stop container
docker start matcha<br>
docker stop matcha<br>
#### Connect to database
driver = org.postgresql.Driver<br>
url = jdbc:postgresql://localhost:5432/matcha<br>
username = admin<br>
password = pass<br>

DATABASE />
