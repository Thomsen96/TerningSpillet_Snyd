#!/bin/bash
#Få fat i porten
echo 'porten er' \"$1\"

kill $(ps aux | grep 'Server.ServerLogik [0-9]* [0-9] '$1 | awk '{print $2}')
