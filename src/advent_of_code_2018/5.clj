(ns advent-of-code-2018.5
  (:require [advent-of-code-2018.input :as input]
            [clojure.string :as string]))

(def polymer (first (input/lines 5)))

(defn is-pair [char1 char2]
  (and  (= (string/lower-case char1) (string/lower-case char2))
        (not= char1 char2)))

(defn react [seen unseen]
  (cond
    (empty? unseen)
      seen
    (empty? seen)
      (recur (list (first unseen)) (rest unseen))
    (is-pair (first seen) (first unseen))
      (recur (rest seen) (rest unseen))
    :else
      (recur (conj seen (first unseen)) (rest unseen))))

(defn chain-react-2 [polymer]
  (react '() (seq polymer)))

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
(defn strip-2 [polymer unit]
  (filter #(= unit (string/lower-case %)) polymer))

(defn strip [unit]
  (string/replace polymer (re-pattern (str unit "|" (string/upper-case unit))) ""))

(comment
(apply min (map (comp count chain-react strip) a-z))
)
