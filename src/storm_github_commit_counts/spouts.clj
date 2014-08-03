(ns storm-github-commit-counts.spouts
  (:require [backtype.storm [clojure :refer [defspout spout emit-spout!]]]
            [clojure.java.io :refer [resource]]
            [clojure.string :refer [split]]))

(defspout commit-feed-listener ["commit"]
  [conf context collector]
  (let [commits (split (slurp (resource "changelog.txt")) #"\n")]
    (spout
      (nextTuple []
                 (doseq [commit commits]
                   (emit-spout! collector [commit]))))))
