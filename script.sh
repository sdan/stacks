#!/bin/bash
clear

echo "UBUNTU 16.04 with NGINX 1.13.12 ONLY SUPPORTED"

echo "Generating new NGINX Server Block for $USER ..."

echo "Enter the name of your main server block. ex. suryad.com"
read base

echo "Enter the name of your server block. ex. data, for data.suryad.com"
read name

mkdir -p /var/www/"$name"."$base".com/html

chown -R $USER:$USER /var/www/"$name"."$base".com/html


echo "Created directory: /var/www/$name.$base.com/html"
echo "Add index.html to that directory if intended."

echo "Enter main sites-AVAILABLE directory ex. suryad.com for /etc/nginx/sites-available/suryad.com"
read directory

echo "Reverse Proxy... (true or false)"
read rp

echo "Port Number like 8080"
read num

javac ServerConfig.java
java ServerConfig /etc/nginx/sites-available/"$directory" /etc/nginx/sites-available/"$name"."$base".com "$name" "$base".com rp num

ln -s /etc/nginx/sites-available/"$name"."$base".com /etc/nginx/sites-enabled/

nginx -t

echo "Restarting server..."

systemctl restart nginx
