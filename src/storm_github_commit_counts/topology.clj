(ns storm-github-commit-counts.topology
  (:require [storm-github-commit-counts
             [spouts :refer [commit-feed-listener]]
             [bolts :refer [email-extractor email-counter]]]
            [backtype.storm [clojure :refer [topology spout-spec bolt-spec]] [config :refer :all]])
  (:import [backtype.storm LocalCluster LocalDRPC]))

(defn github-commit-count-topology []
  (topology
   {"1" (spout-spec commit-feed-listener)}

   {"2" (bolt-spec {"1" :shuffle} email-extractor)
    "3" (bolt-spec {"2" ["email"]} email-counter)}))

(defn run! [& {debug "debug" workers "workers" :or {debug "false" workers "1"}}]
  (let [cluster (LocalCluster.)]
    (.submitTopology cluster
                     "github-commit-count-topology"
                     {TOPOLOGY-DEBUG (Boolean/parseBoolean debug)
                      TOPOLOGY-WORKERS (Integer/parseInt workers)}
                     (github-commit-count-topology))
  (Thread/sleep 10000)
  (.killTopology cluster "github-commit-count-topology")
  (.shutdown cluster)))
