(ns advent-of-code-2018.3a
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

(->> (input/lines 3)
     (map claim)
     (mapcat square-inches)
     frequencies
     (filter (fn [[s-i freq]]
               (<= 2 freq)))
     count)
