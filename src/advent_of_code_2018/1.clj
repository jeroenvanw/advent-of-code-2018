(ns advent-of-code-2018.1
  (:require [advent-of-code-2018.input :as input]
            [meander.epsilon :as m]))

(def input (take 10 (input/lines 1)))

(comment
(reduce + (map load-string (input/lines 1)))
)

;; part 2

(defn absorb [resulting-freqs changes resulting-freq]
  (let [new-resulting-freq (+ resulting-freq (first changes))]
    (if (contains? resulting-freqs new-resulting-freq)
      new-resulting-freq
      (recur
        (conj resulting-freqs new-resulting-freq)
        (rest changes)
        new-resulting-freq))))

(comment
(absorb #{} (cycle (map load-string (input/lines 1))) 0)
)

(comment

  (defn progressions
    [coll]
    (reductions (fn [acc v] (conj acc v)) [] coll))

  ;; works fine with low input, but very slow for actual input
  (->> (input/lines 1)
       (cycle)
       (map load-string)
       (reductions +)
       (progressions)
       (map frequencies)
       (keep #(->> %
                   (filter (fn [[k v]] (= 2 v)))
                   (map (fn [[k v]] k))
                   (first)))
       (first))

  1)
