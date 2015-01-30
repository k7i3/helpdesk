#!/bin/bash
mvn package
asadmin undeploy helpdesk-tnc-web
asadmin deploy target/helpdesk-tnc-web.war