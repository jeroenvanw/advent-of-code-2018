(ns advent-of-code-2018.6
  (:require [advent-of-code-2018.input :as input]
            [clojure.string :as string]))

;; ([x y] ...)
(def coordinates
  (let [lines (input/lines 6)
        ->coordinate (fn [line] (vec (map #(Integer/parseInt %) (string/split line #", "))))]
    (map ->coordinate (take (min 15 (count lines)) lines))))

(defn x [[x y]] x)

(defn y [[x y]] y)

;; construct k-d tree of input coordinates (Brown, 2015)

;; for each location, find nearest neighbor in k-d tree
;; (defn nn "returns nearest neighbor of coordinate in kd-tree" [location] )

(def sorted-by-x (vec (sort-by x coordinates)))
(def sorted-by-y (vec (sort-by y coordinates)))

(defn make-node [value]
  {:value value :left-child nil :right-child nil})

(defn leaf? [node]
  (and  (nil? (node :left-child))
        (nil? (node :right-child))))

(defn set-left-child [parent child]
  (update parent :left-child (fn [_] (identity child))))

  (defn set-right-child [parent child]
    (update parent :right-child (fn [_] (identity child))))
