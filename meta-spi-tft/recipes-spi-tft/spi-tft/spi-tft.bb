LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-or-later;md5=fed54355545ffd980b814dab4a3b312c"

SRC_URI = "git://git@github.com/ajdonich/spi-device-driver.git;protocol=https;branch=main"
SRCREV = "eefa764fb5181a97e159df933a30fdc46282ed2e"

S = "${WORKDIR}/git"
#S = "${UNPACKDIR}"

inherit deploy
inherit module

RPROVIDES:${PN} += "kernel-module-tftdriver"
FILES:${PN}:append = "\
    ${ROOT_HOME}/loadmodule.sh \
    ${ROOT_HOME}/TftGifStreamer \
"

TARGET_CC_ARCH += "${LDFLAGS}"
DEPENDS += "dtc-native"

do_compile_dtb () {
    dtc -q -I dts -O dtb -o ${S}/ilitft.dtbo ${S}/devicetree/ilitft.dtso
}

do_install_dtb () {
    install -Dm 0644 ${S}/ilitft.dtbo ${DEPLOY_DIR_IMAGE}/ilitft.dtbo
}

do_compile:append () {
    oe_runmake -C ${S}/examples
}

do_install:append () {
    install -d ${D}${ROOT_HOME}
    install -m 0774 ${S}/loadmodule.sh ${D}${ROOT_HOME}
    install -m 0774 ${S}/examples/TftGifStreamer ${D}${ROOT_HOME}
}

addtask compile_dtb before do_compile
addtask install_dtb before do_install

