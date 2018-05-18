#!/bin/bash

CWD=`pwd`
STAGE="${CWD}/site"

echo "Stage site into ${STAGE} ..."

if [ -d "${STAGE}" ] ; then
  echo "Delete old stage ${STAGE} ..."
  rm -rfv "${STAGE}"
fi

echo "Make stage: ${STAGE}."
mkdir -pv "${STAGE}"
time pmvn clean install site site:stage \
    -DskipTests=true \
    -DstagingDirectory="${STAGE}"

echo "Site generated into: ${STAGE}"
echo "Finished :-)"
