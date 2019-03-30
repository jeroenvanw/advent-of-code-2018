(ns advent-of-code-2018.5
  (:require [advent-of-code-2018.input :as input]
            [clojure.string :as string]))

(def reaction-patterns
  (let [a-z (map char (range 97 123))
        aA-pairs (map #(str % (string/upper-case %)) a-z)]
    (concat aA-pairs (map string/reverse aA-pairs))))

(def regexp (re-pattern (string/join "|" reaction-patterns)))

(defn chain-react [polymer]
  (let [post-reaction (string/replace polymer regexp "")]
  (if (= (count polymer) (count post-reaction))
    polymer
    (recur post-reaction))))

(let [polymer (first (input/lines 5))]
  (count (chain-react polymer)))
