#!/bin/bash

BUILD_DIR="target"
TARGET="${BUILD_DIR}/sloccount.sc"

if [ -e "${TARGET}" ] ; then
    rm -rf "${TARGET}"
fi

if [ ! -d "${BUILD_DIR}" ] ; then
    mkdir -p "${BUILD_DIR}"
fi

sloccount --duplicates --wide --details --append \
    commons-application/src/main/java \
    commons-concurrent/src/main/java \
    commons-config/src/main/java \
    commons-experimental/src/main/java \
    commons-guava/src/main/java \
    commons-jcommander/src/main/java \
    commons-parse/src/main/java \
    commons-shell/src/main/java \
    commons-string/src/main/java \
    commons-swing/src/main/java \
    commons-system/src/main/java \
    commons-testing/src/main/java \
    commons-time/src/main/java \
    commons-uri/src/main/java \
    commons-validate/src/main/java 
sloccount --cached > "${TARGET}"