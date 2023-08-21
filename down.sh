#!/bin/bash

if ! cd .docker; then
    echo ".docker directory is missing"
    exit 1
fi
sudo docker-compose down