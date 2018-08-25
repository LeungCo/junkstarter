# Setting Up Minikube Locally

## Requirements

Make sure you have [brew](https://brew.sh) installed before continuing.

# Super Quickstart

The following script runs the commands in [Installation](#installation) & [Quickstart](#quickstart) for you so you can be up and running in no time!

```sh
bash <(curl -L https://git.io/fAqhG)
```

## Installation

Run the following in terminal:

```sh
brew cask install virtualbox && \
brew cask install minikube && \
brew install kubernetes-cli
```

## Quickstart

Run the following command inside of this projects root directory.

```sh
eval $(minikube docker-env) && \
minikube start && \
kubectl create -f cloudbuild.yaml
```

## Starting Minikube

```sh
minikube start --vm-driver=virtualbox
```

## Linking Docker Daemon to Local Cache

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

# Using Docker-Compose with Minikube

See [this](https://kubernetes.io/docs/tasks/configure-pod-container/translate-compose-kubernetes/#before-you-begin) article for setup instructions.

Once the configs are created run the following command:

```sh
kubectl create -f database-service.yaml,database-deployment.yaml,appserver-service.yaml,appserver-deployment.yaml
```

The configs that are generated may need some modification to get them communicating to the host correctly. Generally adding `NodePort` to the appserver config will get this working as expected.
