(ns advent-of-code-2018.5
  (:require [advent-of-code-2018.input :as input]
            [clojure.string :as string]))

(def polymer (first (input/lines 5)))

(def a-z (map char (range 97 123)))

(def reaction-patterns
  (let [aA-pairs (map #(str % (string/upper-case %)) a-z)]
    (concat aA-pairs (map string/reverse aA-pairs))))

(def regexp (re-pattern (string/join "|" reaction-patterns)))

(defn chain-react [polymer]
  (let [post-reaction (string/replace polymer regexp "")]
  (if (= (count polymer) (count post-reaction))
    polymer
    (recur post-reaction))))

;; part 1

(comment
(count (chain-react polymer))
)

;; part 2

(defn strip [unit]
  (string/replace polymer (re-pattern (str unit "|" (string/upper-case unit))) ""))

(comment
(apply min (map (comp count chain-react strip) a-z))
)
