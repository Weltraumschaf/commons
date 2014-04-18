#!/bin/bash

CWD=`pwd`
STAGE="${CWD}/staged-site"

if [ -d "${STAGE}" ] ; then
  rm -rfv "${STAGE}"
fi

mkdir -pv "${STAGE}"
mvn clean install site site:stage -DstagingDirectory="${STAGE}"
