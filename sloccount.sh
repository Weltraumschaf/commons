#!/bin/bash

TARGET=target/sloccount.sc

rm -rf "${TARGET}"

sloccount --duplicates --wide --details --append \
    commons-application/src/main/java \
    commons-concurrent/src/main/java \
    commons-config/src/main/java \
    commons-experimental/src/main/java \
    commons-guava/src/main/java \
    commons-jcommander/src/main/java \
    commons-shell/src/main/java \
    commons-string/src/main/java \
    commons-swing/src/main/java \
    commons-system/src/main/java \
    commons-testing/src/main/java \
    commons-time/src/main/java \
    commons-validate/ > "${TARGET}"
