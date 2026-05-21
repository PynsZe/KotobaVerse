#!/bin/bash
set -e
set -a              # active l'auto-export : toute variable assignée devient env var
source .env
set +a              # désactive

docker compose up -d --wait
./gradlew :server:run