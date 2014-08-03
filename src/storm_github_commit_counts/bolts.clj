(ns storm-github-commit-counts.bolts
  (:require [backtype.storm [clojure :refer [emit-bolt! defbolt ack! bolt]]]
            [clojure.string :refer [split]]))

(defbolt email-extractor ["email"]
  [{commit :commit :as tuple} collector]
  (let [[_ email] (split commit #" ")]
    (emit-bolt! collector [email])))

(defbolt email-counter [] {:prepare true}
  [conf context collector]
  (let [counts (atom {})]
    (bolt
      (execute [{email :email :as tuple}]
               (swap! counts #(update-in % [email] (fnil inc 0)))
               (doseq [[email c] @counts]
                 (println email "has count of" c))))))
