(ns advent-of-code-2018.1
  (:require [advent-of-code-2018.input :as input]))

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
