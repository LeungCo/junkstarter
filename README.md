# Junk Starter

[![Build Status](https://travis-ci.org/LeungCo/junkstarter.svg?branch=master)](https://travis-ci.org/LeungCo/junkstarter)


# How to use Documentation

See the introduction page [here](./docs/README.md).

# Backend - Getting Started

## Requirements

This example uses features in Docker 17.05 CE Edge. Install this version to run the example.

## Secrets

This application uses Docker secrets to secure the application components. The reverse proxy requires creating a certificate that is stored as a secret and the payment also requires a password stored as a secret. To create a certificate and add as a secret:

```
mkdir certs

openssl req -newkey rsa:4096 -nodes -sha256 -keyout certs/domain.key -x509 -days 365 -out certs/domain.crt

docker secret create revprox-cert certs/domain.crt

docker secret create revprox-key certs/domain.key

docker secret create postgres-password certs/domain.key
```

To create a secret for staging the payment gateway:

```
echo staging | docker secret create staging-token - 
```

## Run as an application

To run Junk Starter as an application:
```
docker-compose up --build
```

## Deploy to a swarm
```
#If you need to create a Swarm
docker swarm init
docker stack deploy -c docker-stack.yml junkstarter
```

## A simplified development environment
This compose file creates a simplified development environment consisting of only the application server and the database.

```
docker-compose --file docker-compose-dev.yml up --build
```

## The Application

The URL for the content is `http://localhost:8080/`

# REST API

Documentation for REST calls: [REST API](./REST.md)

# Frontend - Getting Started

1. `cd` into `app/frontend`
2. Run `yarn start` to begin the local development server
3. The default page should open in your browser which is generally `http://localhost:3000`

> The local dev server has hot reloading enabled so any changes made to source files will cause the page to refresh dynamically for you.
