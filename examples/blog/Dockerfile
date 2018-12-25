FROM debian:stretch-slim

LABEL version="base" maintainer="ales.loncar@celavi.org"

RUN apt-get update \
    && apt-get install -y --no-install-recommends \
    ca-certificates \
    libpq-dev \
    dumb-init \
    perl \
    cpanminus \
    build-essential \
    procps \
    && cpanm Carton \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

COPY . /opt/app
WORKDIR /opt/app

RUN carton install --deployment

RUN rm -rf /root/.cpanm \
    && apt-get remove --purge -y build-essential \
    && apt-get autoremove -y \
    && rm -rf /tmp/* \
    && rm -rf /var/tmp/*

RUN groupadd -r app \
   && useradd -r -g app app \
   && chown app:app -R /opt/app

USER app

# Expose the default web server port
EXPOSE 8080

# Runs "/usr/bin/dumb-init -- /my/script --with --args"
ENTRYPOINT ["/usr/bin/dumb-init", "--"]

# Run this command when container is started
CMD carton exec hypnotoad -f script/blog
