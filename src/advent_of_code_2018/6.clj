(ns advent-of-code-2018.6
  (:require [advent-of-code-2018.input :as input]
            [clojure.string :as string]))

;; ([x y] ...)
(def coordinates
  (let [lines (input/lines 6)
        ->coordinate (fn [line] (vec (map #(Integer/parseInt %) (string/split line #", "))))]
    (map ->coordinate lines)))

(defn x [[x y]] x)

(defn y [[x y]] y)

(defn distance [c1 c2]
  (+  (Math/abs (- (x c1) (x c2)))
      (Math/abs (- (y c1) (y c2)))))
