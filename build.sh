#!/bin/bash

# Note: expects Yocto required APT packages installed on build host, see:
# https://docs.yoctoproject.org/brief-yoctoprojectqs/index.html#build-host-packages

cd poky
source oe-init-build-env # Creates local.conf and bblayers.conf in build/conf
cp ../../local.conf.base conf/local.conf # Overwrite with base file
cp ../../bblayers.conf.base conf/bblayers.conf # Overwrite with base file
bitbake core-image-spi-tft

exit $?


