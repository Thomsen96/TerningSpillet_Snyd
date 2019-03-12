#!/bin/bash
kill $(ps aux | grep 'Server.ServerLogik' | awk '{print $2}')

