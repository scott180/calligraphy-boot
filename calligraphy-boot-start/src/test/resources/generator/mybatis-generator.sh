#!/bin/bash

cd ./
mvn  -Dmybatis.generator.overwrite=true -Dmybatis.generator.configurationFile=src/test/resources/generator/generatorConfig.xml mybatis-generator:generate
