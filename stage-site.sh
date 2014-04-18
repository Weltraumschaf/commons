#!/bin/bash

CWD=`pwd`
STAGE="${CWD}/staged-site"

if [ -d "${STAGE}" ] ; then
  echo "Delete old stage ${STAGE} ..."
  rm -rfv "${STAGE}"
fi

echo "Make stage: ${STAGE}."
mkdir -pv "${STAGE}"
time mvn clean install site site:stage -DstagingDirectory="${STAGE}"

echo "Finished :-)"
