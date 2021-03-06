;; an optimized approach (~10 times faster)

(ns advent-of-code-2018.5-2
  (:require [advent-of-code-2018.input :as input]
            [clojure.string :as string]))

;; The input text is a single line that ends in a newline character.
;; (first (input/lines 5)) gets the input as a string without the newline character.
(def polymer (seq (first (input/lines 5))))

(defn pair? [char1 char2]
  (and  (= (string/lower-case char1) (string/lower-case char2))
        (not= char1 char2)))

(defn remove-pairs [seen unseen]
  (cond
    (empty? unseen)
      seen
    (empty? seen)
      (recur (list (first unseen)) (rest unseen))
    (pair? (first seen) (first unseen))
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

;; (Character/toLowerCase \A) inlined is 10 times faster than (first (string/lower-case \A))
;; inline. However, when not inline, Character/toLowerCase is thirty times slower
;; clojure.string/lower-case outputs a string, not a character.
;; first is applied to turn the string (containing a single character)
;; into a character.
(def units (set (map (comp first string/lower-case) (set polymer))))

(defn strip [unit]
  (filter #(not= unit (first (string/lower-case %))) polymer))

(comment
(apply min (map (comp count react strip) units))
)
