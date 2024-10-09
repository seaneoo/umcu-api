# UpcomingMCU API

UpcomingMCU (UMCU) API is a free, consumable-only REST API to retrieve data about the productions within the [Marvel
Cinematic Universe (MCU)](https://en.wikipedia.org/wiki/Marvel_Cinematic_Universe).

[![GitHub Release](https://img.shields.io/github/v/release/seaneoo/umcu-api?include_prereleases&label=latest%20release)](https://github.com/seaneoo/umcu-api/releases) [![Build Boot Jar](https://github.com/seaneoo/umcu-api/actions/workflows/build-boot-jar.yml/badge.svg)](https://github.com/seaneoo/umcu-api/actions/workflows/build-boot-jar.yml) [![Build Docker Image](https://github.com/seaneoo/umcu-api/actions/workflows/build-docker-image.yml/badge.svg)](https://github.com/seaneoo/umcu-api/actions/workflows/build-docker-image.yml)

## Installation

Before starting, ensure you have Docker and Docker Compose installed.

1. [Obtain an "API Read Access Token" from TMDB.](https://www.themoviedb.org/settings/api) This token will be used in
   step 3.

1. Create a folder for the project. The location does not matter (such as the `home` directory).

```bash
mkdir umcu
cd umcu
```

2. Run the following commands to download the required files. Make sure `default.conf.template` is in the `nginx`
   directory.

```bash
wget https://raw.githubusercontent.com/seaneoo/umcu-api/refs/heads/main/docker/compose.yaml
wget https://raw.githubusercontent.com/seaneoo/umcu-api/refs/heads/main/docker/nginx/default.conf.template --directory-prefix=nginx/default.conf.template
```

3. Set the following environment variables on your machine. It is recommended that `MONGO_USERNAME` and `MONGO_PASSWORD`
   are secure, random strings.

```bash
export MONGO_USERNAME=[change me]
export MONGO_PASSWORD=[change me]
export TMDB_API_KEY=[your api read access token]
export TMDB_LIST_ID=8289533
```

4. Run the Docker container.

```bash
docker compose -f compose.yaml up -d
```

## Data

This product uses the TMDB API but is not endorsed or certified by TMDB.

Check them out here: [The Movie Database](https://www.themoviedb.org/?language=en-US).

## License

[GNU GPLv3](LICENSE)
