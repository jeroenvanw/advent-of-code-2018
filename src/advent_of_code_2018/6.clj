(ns advent-of-code-2018.6
  (:require [advent-of-code-2018.input :as input]
            [clojure.string :as string]))

;; ([x y] ...)
(def input
  (let [lines (input/lines 6)
        ->coordinate (fn [line] (vec (map #(Integer/parseInt %) (string/split line #", "))))]
    (map ->coordinate (take (min 15 (count lines)) lines))))

(defn x [[x y]] x)

(defn y [[x y]] y)

;; construct k-d tree of input coordinates (Brown, 2015)

;; for each location, find nearest neighbor in k-d tree
;; (defn nn "returns nearest neighbor of coordinate in kd-tree" [location] )

(def sorted-by-x (vec (sort-by x input)))
(def sorted-by-y (vec (sort-by y input)))

(comment
  (let [median
        left-of-median
        right-of-median]
      {:value median
        :left-child (recur left-of-median)
        :right-child (recur right-of-median)})
)

(defn make-pathed-value [path value]
  [path value])

(defn insert-pathed-value [tree [path value]]
  (assoc-in tree (conj path :value) value))

(comment
  (defn nearest-letter [coordinate] (apply min (map #(distance coordinate %))))
  (pmap nearest-letter coordinates)
)

(defn make-job "job: The subtree rooted at the end of %path contains %coordinates; the root of this subtree splits %dimension."
  [path dimension coordinates]
  {:path path :dimension dimension :coordinates coordinates})

(def dimensions #{:x :y})

(defn trisect "Returns a map containing - in %dimension - the median of %coordinates, the coordinates ordered before the median and the coordinates ordered after the median."
  [coordinates dimension]
  (let [halves (split-at (/ (count coordinates) 2) coordinates)
        last-of-halves (last halves)
        after-median (if (empty? last-of-halves) nil last-of-halves)]
    { :median (last (first halves))
      :before-median (butlast (first halves))
      :after-median after-median}))

(def elements (range 0 7))

(defn other-dimension [dimension]
  (case dimension
    :x :y
    :y :x))

(defn build-tree [tree jobs]
  (if (empty? jobs)
    tree
    (let [job (first jobs)
          coordinates (job :coordinates)
          path (job :path)]
      (if (empty? coordinates)
        (recur (assoc-in tree path nil) (rest jobs))
        (let [dimension (job :dimension)
              trisection (trisect coordinates dimension)
              median (trisection :median)
              before-median (trisection :before-median)
              after-median (trisection :after-median)
              job-before (make-job
                          (conj path :before)
                          (other-dimension dimension)
                          before-median)
              job-after (make-job
                          (conj path :after)
                          (other-dimension dimension)
                          after-median)]
          (recur
            (assoc-in tree (conj path :value) median)
            (conj (rest jobs) job-before job-after)))))))

(comment
(build-tree {} (list (make-job [] :x (map inc elements))))
)
;;├ ─ └ │ ┌
;   ┌ 7
; ┌ 6
; │ └ 5
; 4
; │ ┌ 3
; └ 2
;   └ 1
