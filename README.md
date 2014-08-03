# storm-github-commit-counts

A reimplementation of Chapter 2 in Clojure from [Storm Applied](http://www.manning.com/sallen/)

## Usage

To run on a local cluster:

```bash
lein run -m storm-github-commit-counts.topology/run!
# OR
lein run -m storm-github-commit-counts.topology/run! debug false workers 10
```

To run on a distributed cluster:

```bash
lein uberjar
# copy jar to nimbus, and then on nimbus:
bin/storm jar path/to/uberjar.jar storm-github-commit-counts.TopologySubmitter workers 30 debug false
```

or use `[storm-deploy](https://github.com/nathanmarz/storm-deploy/wiki)`
