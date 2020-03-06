#!/bin/bash
java -javaagent:./elastic-apm-agent-1.13.0.jar \
     -Delastic.apm.service_name=$APM_APPLICATION_NAME \
     -Delastic.apm.server_url=$APM_SERVER_URL \
     -Delastic.apm.secret_token=$APM_SECRET_TOKEN \
     -Delastic.apm.application_packages=com.tnki.core \
     -jar /app/build/libs/core-latest.jar
