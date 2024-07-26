#!/bin/bash

#
# Copyright (c) 2021-2024. Bernard Bou.
#

IN="$1"
if [ -z "$1" ]; then
	IN=yaml
fi
echo "YAML:  ${IN}" 1>&2;

IN2="$2"
if [ -z "$2" ]; then
	IN2=yaml2
fi
echo "YAML2: ${IN2}" 1>&2;

OUT="$3"
if [ -z "$3" ]; then
	OUT=json/oewn.json
fi
mkdir -p "${OUT}"
echo "OUT:   ${OUT}" 1>&2;

jar=target/yaml2json-2.1.1-uber.jar
java -ea -jar "${jar}" "${IN}" "${IN2}" "${OUT}"
