# Mojo Blog

Mojo Blog is simple example with [DBD::Pg](https://metacpan.org/pod/DBD::Pg) that makes [PostgreSQL](https://www.postgresql.org) a lot of fun to use with the [Mojolicious](https://mojolicious.org) real-time web framework.

## Local installation

### Perl

You can use system Perl version for development. I recommend the use of `perlbrew` or `plenv` environment.

- [perlbrew](https://perlbrew.pl/)
- [plenv](https://github.com/tokuhirom/plenv/)

### Prerequisites

Cpanminus and Carton are the only prerequisite for running the application. All required modules/dependencies are then installed via Carton from cpanfile.

- [App::cpanminus - get, unpack, build and install modules from CPAN](https://github.com/miyagawa/cpanminus/tree/devel/App-cpanminus)
- [Carton - Perl module dependency manager (aka Bundler for Perl)](https://metacpan.org/pod/Carton)

### Configuration

For Perl-ish configuration Mojolicious plugin is used - [Mojolicious::Plugin::Config](http://mojolicious.org/perldoc/Mojolicious/Plugin/Config).

## Building the Docker container image

### Building the latest image

```bash
$ docker build -t <NAME:TAG> .
```

### Running tests inside container image

```bash
$ docker run --rm -v $PWD/t:/opt/app/t <IMAGE> carton exec prove -lr -j4
```

## Working with Helm

### Install `tiller` in the Minikube cluster

```bash
$ helm init
```

### We can do a dry-run of a helm install and enable debug to inspect the generated definitions:

```bash
$ helm install --dry-run --debug --namespace=default ./helm
```

### Install

```bash
$ helm install --name mojo-blog --namespace=default ./helm
```

### Check the status of the release

```bash
$ helm ls --all mojo-blog
```

### Delete the release

```bash
$ helm del --purge mojo-blog
```

### Do the linting

```bash
$ helm lint ./helm
```

### Render chart templates locally and store them as Kubernetes YAML

```bash
$ helm template --name mojo-blog --namespace=default ./helm  > mojo-blog.yaml
```

## Use Port Forwarding to Access Applications in a Cluster

### Forward a local port to a port on the pod / deployment / service

```bash
$ kubectl port-forward <POD> 8080:8080
# or
$ kubectl port-forward dc/<DEPLOYMENT> 8080:8080
# or
$  kubectl port-forward svc/<SERVICE> 8080:8080
```
