#!/bin/bash
if ! sudo docker build --no-cache -t app .;
then
    echo "Build failed"
    exit 1
fi
if ! cd .docker; then
    echo ".docker directory is missing"
    exit 1
fi
sudo docker-compose up -d
