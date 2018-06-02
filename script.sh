#!/bin/bash
clear

echo "UBUNTU 16.04 with NGINX 1.13.12 ONLY SUPPORTED"

echo "Generating new NGINX Server Block for $USER ..."

echo "Enter the name of your main server block. ex. suryad.com"
read base

echo "Enter the name of your server block. ex. data, for data.suryad.com"
read name

mkdir -p /var/www/"$name"."$base".com/html

chmod -R 755 /var/www

echo "Created directory: /var/www/$name.$base.com/html"
echo "Add index.html to that directory if intended."

echo "Enter sites-AVAILABLE directory"
read directory

cp /etc/nginx/sites-available/"$directory" /etc/nginx/sites-available/"$name"."$base".com
