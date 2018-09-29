
$wd = $pwd

## SQL Server Database

# build the database builder
cd $wd\docker\database
docker build -t junkstarter-db-builder -f Dockerfile.builder .

# build the dacpac for the database schema
rmdir -Force -Recurse out
mkdir out
docker run --rm -v $pwd\out:c:\bin -v $wd\dotnet:c:\src junkstarter-db-builder c:\src\Docker.JunkStarter\Docker.JunkStarter.Database\build.ps1

# build the database image
docker build -t junkstarter-db .


# build the database builder
cd $wd\docker\database
docker build -t junkstarter-db-builder -f Dockerfile.builder .

# build the dacpac for the database schema
rmdir -Force -Recurse out
mkdir out
docker run --rm -v $pwd\out:c:\bin -v $wd\dotnet:c:\src junkstarter-db-builder c:\src\Docker.JunkStarter\Docker.JunkStarter.Database\build.ps1

# build the database image
docker build -t junkstarter-db .


## .NET WebApi
cd $wd\docker\api
docker build -t junkstarter-api-builder -f Dockerfile.builder .

# build the web output
rmdir -Force -Recurse out
mkdir out
docker run --rm -v $pwd\out:c:\bin -v $wd\dotnet:c:\src junkstarter-api-builder c:\src\Docker.JunkStarter\Docker.JunkStarter.Api\build.ps1

# build the web api image
docker build -t junkstarter-api .

cd $wd
