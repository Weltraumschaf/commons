#!/bin/bash

CWD=`pwd`
STAGE="${CWD}/staged-site"

echo "Stage site into ${STAGE} ..."

if [ -d "${STAGE}" ] ; then
  echo "Delete old stage ${STAGE} ..."
  rm -rfv "${STAGE}"
fi

echo "Make stage: ${STAGE}."
mkdir -pv "${STAGE}"
time mvn clean package site site:stage -DstagingDirectory="${STAGE}"

echo "Finished :-)"
