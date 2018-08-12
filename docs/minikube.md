# Setting Up Minikube Locally


## Installation
```sh
brew cask install virtualbox && \
brew cask install minikube && \
brew install kubernetes-cli
```

## Running Minikube Cluster

```sh
minikube start --vm-driver=virtualbox
```

## Link Docker Daemon

Provides you shared access to the docker daemon running inside of minikube. Allows sharing the cache of docker images to prevent having to run them on the host machine and then also inside of minikube.

```sh
eval $(minikube docker-env)
```

## Managing the Kubernetes Cluster

```sh
# Get info about the current running cluster
kubectl cluster-info

# Get info about the current nodes running in the cluster
kubectl get nodes

# List current running deployments and info about their nodes/pods
kubectl get deployments
```

# Building Local Images for Docker to use with Minikube

Run these after running the above `eval` command.

```sh
docker build -t martinwheeler/junkstarter-api:latest ./app
docker build -t martinwheeler/junkstarter-db:latest ./database
```

## Quickstart

```sh
eval $(minikube docker-env) && \
docker build -t martinwheeler/junkstarter-api:latest ./app && \
docker build -t martinwheeler/junkstarter-db:latest ./database && \

minikube start && \
kubectl create -f database-service.yaml,database-deployment.yaml,appserver-service.yaml,appserver-deployment.yaml
```

# Using Docker-Compose with Minikube

https://kubernetes.io/docs/tasks/configure-pod-container/translate-compose-kubernetes/#before-you-begin