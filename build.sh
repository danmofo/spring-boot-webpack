#!/usr/bin/env bash

# Application build script
# ---
# After running you'll end up with the following in the 'dist' directory:
# - A runnable .jar which contains the application
# - A folder containing all compiled, minified and compressed static assets
#
# It's not great because it requires the webpack build to execute first, so that the manifest.json can be
# packaged with the application.

export NODE_ENV="production"

# Clean previous build
rm -rf "dist"
mkdir -p "dist/static"

# Compile static assets
# todo: npm install conditionally
# npm --prefix "./client" install
npm --prefix "./client" run prod
cp -r "client/dist/" "dist/static"

# Generate application
# ./mvnw -f "server/pom.xml" clean package -Dmaven.test.skip=true
./server/mvnw -f "server/pom.xml" clean package
cp -r "server/target/spring-boot-webpack-0.0.1-SNAPSHOT.jar" "dist/"

echo "Application created @ ./dist/"
