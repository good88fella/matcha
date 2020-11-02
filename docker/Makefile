IMAGE_NAME=matcha
PREFIX=21school

build:
	docker build -t ${PREFIX}/${IMAGE_NAME} .

run:
	docker run -d -e POSTGRES_PASSWORD=pass --name matcha -p 5432:5432 ${PREFIX}/${IMAGE_NAME}
