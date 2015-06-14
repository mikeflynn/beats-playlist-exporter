#!/bin/bash

JAR="./beats-playlist-export.uber.jar"

export BEATS_API_KEY=REPLACE ME FROM BEATS APP CONFIG
export BEATS_API_SECRET=REPLACE ME FROM BEATS APP CONFIG
export BEATS_API_TOKEN=REPLACE ME FROM http://thatmikeflynn.com/beats-clj/resources/
export OUTDIR=/tmp

java -jar $JAR