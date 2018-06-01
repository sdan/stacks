#!/bin/bash
clear

echo "UBUNTU 16.04 with NGINX 1.13.12 ONLY SUPPORTED"

echo "Generating new NGINX Server Block for $USER ..."

echo "Enter the name of your main server block"
read base

echo "Enter the name of your server block..."
read name

sudo mkdir -p /var/www/"$name"."$base".com/html

sudo chmod -R 755 /var/www
