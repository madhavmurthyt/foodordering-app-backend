#!/usr/bin/env bash
set -e

export JAVA_HOME=/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home

# Kill existing backend process on port 8080
PID=$(lsof -i :8080 -t 2>/dev/null || true)
if [ -n "$PID" ]; then
  echo "Stopping existing backend (PID $PID)..."
  kill "$PID" 2>/dev/null || true
  sleep 2
fi

cd "$(dirname "$0")"

echo "Building backend..."
mvn clean install -DskipTests -q

echo "Starting backend on http://localhost:8080/api/"
java -jar FoodOrderingApp-api/target/FoodOrderingApp-api-1.0-SNAPSHOT.jar
