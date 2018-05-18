#!/bin/bash

# https://docs.sonatype.org/display/Repository/Sonatype+OSS+Maven+Repository+Usage+Guide

pmvn release:clean && \
pmvn release:prepare -DautoVersionSubmodules=true && \
pmvn release:perform

echo "Finished :-)"
echo "Goto https://oss.sonatype.org/"
