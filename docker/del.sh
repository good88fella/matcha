#!/bin/bash
NAME=matcha
PREFIX=21school

docker stop ${NAME}
docker rm ${NAME}
docker rmi ${PREFIX}/${NAME}
