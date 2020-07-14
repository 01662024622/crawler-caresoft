#!/usr/bin/env bash
./mvnw clean install -DskipTests
docker-compose build
