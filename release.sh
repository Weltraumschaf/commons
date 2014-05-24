#!/bin/bash

# https://docs.sonatype.org/display/Repository/Sonatype+OSS+Maven+Repository+Usage+Guide

mvn release:clean
mvn release:prepare && mvn release:perform

echo "Finished :-)"
echo "Goto https://oss.sonatype.org/"
