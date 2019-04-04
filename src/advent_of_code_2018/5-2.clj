;; an optimized approach (~1000 times faster)

(ns advent-of-code-2018.5-2
  (:require [advent-of-code-2018.input :as input]
            [clojure.string :as string]))

(def polymer (seq (first (input/lines 5))))

(defn is-pair [char1 char2]
  (and  (= (string/lower-case char1) (string/lower-case char2))
        (not= char1 char2)))

(defn remove-pairs [seen unseen]
  (cond
    (empty? unseen)
      seen
    (empty? seen)
      (recur (list (first unseen)) (rest unseen))
    (is-pair (first seen) (first unseen))
      (recur (rest seen) (rest unseen))
    :else
      (recur (conj seen (first unseen)) (rest unseen))))

(defn react [polymer]
  (remove-pairs '() polymer))

;; part 1
(comment
(count (react polymer))
)

;; part 2
(def units (set (map (comp first string/lower-case) (set polymer))))

(defn strip [unit]
  (filter #(not= unit (first (string/lower-case %))) polymer))

(comment
(apply min (map (comp count react strip) units))
)
