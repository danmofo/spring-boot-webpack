# Spring Boot + Webpack example

A sample Spring Boot application that makes use of Webpack 2 for static assets, with hot reloads enabled for both
Freemarker templates + static assets.

## Prerequisites

- NodeJS + Npm installed on your system

## Developing

- Start your Spring Boot application via your IDE.
- Run `npm run watch` from the client directory.
- Visit `localhost:9090`, note the port is **9090**, not the port defined in `application.properties`, if you try and visit 8080 all
asset URLs will 404.

## Building

To build the application, run `./build.sh`.

## Notes

- If modifying the server port, you need to also update the Webpack dev server config.
