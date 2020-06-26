(ns advent-of-code-2018.3b
  (:require [advent-of-code-2018.input :as input]))

(defn claim [line]
  (let [[id l t w h] (->> (re-find #"#(?<id>\d+) @ (?<t>\d+),(?<l>\d+): (?<w>\d+)x(?<h>\d+)"
                                   line)
                          rest
                          (map read-string))]
    {:id id
     :left l
     :top t
     :width w
     :height h}))

(defn square-inches [claim]
  (for [x (map #(+ (:left claim) %) (range (:width  claim)))
        y (map #(+ (:top  claim)  %) (range (:height claim)))]
    [x y]))

(def inp ["#1 @ 1,3: 4x4"
          "#2 @ 3,1: 4x4"
          "#3 @ 5,5: 2x2"])

(let [claims (map claim
                  (or inp (input/lines 3)))
      uncontested (->> claims
                       (map square-inches)
                       frequencies
                       (filter (fn [[k v]]
                                 (= 1 v)))
                       (map first)
                       set)]
  uncontested)
  (->> claims
       (filter #(->> %
                     square-inches
                     (every? uncontested)))
       first
       :id))
