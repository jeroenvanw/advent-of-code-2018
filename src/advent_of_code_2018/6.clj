(ns advent-of-code-2018.6
  (:require [advent-of-code-2018.input :as input]
            [clojure.string :as string]
            [clojure.core.async :as async]))

;; ([x y] ...)
(def coordinates
  (let [lines (input/lines 6)
        ->coordinate (fn [line] (vec (map #(Integer/parseInt %) (string/split line #", "))))]
    (map ->coordinate (take (min 5 (count lines)) lines))))

(defn x [[x y]] x)

(defn y [[x y]] y)

;; construct k-d tree of input coordinates (Brown, 2015)

;; for each location, find nearest neighbor in k-d tree
;; (defn nn "returns nearest neighbor of coordinate in kd-tree" [location] )

(defn make-node [value]
  {:value value :left-child nil :right-child nil})

(defn leaf? [node]
  (and  (nil? (node :left-child))
        (nil? (node :right-child))))

(defn put-left-child [parent child]
  (update parent :left-child (fn [_] child)))

(defn put-right-child [parent child]
  (update parent :right-child (fn [_] child)))

(def x:y (sort-by x (sort-by y coordinates)))
(def y:x (sort-by y (sort-by x coordinates)))
