;; work in progress
(ns tree.bst)

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
        (recur tree (rest jobs))
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

(defn tree-contains? [tree n]
  (let [value (tree :value)]
    (cond
      (= value n)
        true
      (> value n)
        (if (tree :before)
          (recur (tree :before) n)
          false)
      :else ;;(> value n)
        (if (tree :after)
          (recur (tree :after) n)
          false))))

(comment
(def tree (build-tree {} (list (make-job [] :x (range 1 8)))))
(defn make-tree [n]
  (build-tree {} (list (make-job [] :x (range 1 n)))))
(defn time-make-tree [n]
  (time (make-tree (reduce * (repeat n 2)))) nil)
)
;;├ ─ └ │ ┌
;   ┌ 7
; ┌ 6
; │ └ 5
; 4
; │ ┌ 3
; └ 2
;   └ 1
