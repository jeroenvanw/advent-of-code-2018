(ns advent-of-code-2018.1
  (:require [advent-of-code-2018.input :as input]))

(reduce + 0 (map load-string (input/lines 1)))
