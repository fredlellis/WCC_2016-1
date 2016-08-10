(ns wcc-2016-01.coreTest
  (:require [clojure.test :refer :all]
            [clojure.math.combinatorics :refer :all]))


(defn m [x y](->>(subsets y)(filter(fn[i](<(reduce + i)x)))(apply sorted-set-by #(>(reduce + %1)(reduce + %2)))(first)))

(defn wwwc-01-filter [max array]
  (first (apply sorted-set-by (fn [x y] (> (reduce + x) (reduce + y)))
                (filter (fn [item] (< (reduce + item) max))
                        (subsets array)))))


(defn wwwc-01-loop [x array]
  (loop
    [combinacoes (subsets array)
     array-maior-menores []]
    (if (empty? combinacoes)
      (vec array-maior-menores)
      (recur (rest combinacoes) (if (and (< (reduce + (first combinacoes)) x) (> (reduce + (first combinacoes)) (reduce + array-maior-menores)))
                                  (first combinacoes)
                                  array-maior-menores)))))

(deftest a-test
  (testing "Maior menor"
    (is (= (wwwc-01-loop 29 [2, 8, 3, 9, 11]) [8 9 11]))
    (is (= (wwwc-01-filter 29 [2, 8, 3, 9, 11]) [8 9 11]))
    (is (= (wwwc-01-filter 29 [2, 8, 3, 9, 11, -4]) [8 9 11]))
    (is (= (wwwc-01-filter 31 [2, 8, 3, 9, 11, -4, 34]) [-4 34]))
    (is (= (m 31 [2, 8, 3, 9, 11, -4, 34]) [-4 34]))))


(do
  (time (wwwc-01-filter 29 [2, 8, 3, 9, 11]))
  (time (wwwc-01-loop 29 [2, 8, 3, 9, 11]))
  (time (m 29 [2, 8, 3, 9, 11])))
