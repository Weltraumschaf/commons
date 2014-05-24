#!/bin/bash

# https://docs.sonatype.org/display/Repository/Sonatype+OSS+Maven+Repository+Usage+Guide

set -e

RELEASE_VERSION="${1}"

if [ "${RELEASE_VERSION}" == "" ]; then
    echo "No release version given!";
    exit 1;
fi

NEXT_VERSION="${2}"

if [ "${NEXT_VERSION}" == "" ]; then
    echo "No next development version given!";
    exit 1;        
fi

PASSPHRASE=${3}

if [ "${PASSPHRASE}" == "" ]; then
    echo "No passphrase given!";
    exit 1;        
fi

mvn release:clean
mvn --batch-mode -Dgpg.passphrase='${PASSPHRASE}' \
                 -Dtag=${RELEASE_VERSION} release:prepare \
                 -DreleaseVersion=${RELEASE_VERSION} \
                 -DdevelopmentVersion=${NEXT_VERSION} && \
    mvn release:perform

echo "Finished :-)"
echo "Goto https://oss.sonatype.org/"
