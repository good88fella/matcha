NAME=matcha
PREFIX=21school

build:
	docker build -t ${PREFIX}/${NAME} .

run:
	docker run -d -e POSTGRES_PASSWORD=pass --name ${NAME} -p 5432:5432 ${PREFIX}/${NAME}

start:
	docker start ${NAME}

all: build run
