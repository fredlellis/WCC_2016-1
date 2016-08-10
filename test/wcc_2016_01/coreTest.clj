(ns wcc-2016-01.coreTest
  (:require [clojure.test :refer :all]
            [clojure.math.combinatorics :refer :all]))

;;Twitter
(defn t [x y](->> (subsets y) (filter #(< (reduce + %) x)) (apply sorted-set-by #(> (reduce + %1) (reduce + %2))) (first)))

;;Solução usando filter
(defn wcc-01-filter [max array]
  (first (apply sorted-set-by (fn [x y] (> (reduce + x) (reduce + y)))
                (filter (fn [item] (< (reduce + item) max))
                        (subsets array)))))

;;Solução usando Recursividade
(defn wcc-01-loop [x array]
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
    (is (= (wcc-01-loop 29 [2, 8, 3, 9, 11]) [8 9 11]))
    (is (= (wcc-01-filter 29 [2, 8, 3, 9, 11]) [8 9 11]))
    (is (= (wcc-01-filter 29 [2, 8, 3, 9, 11, -4]) [8 9 11]))
    (is (= (wcc-01-filter 31 [2, 8, 3, 9, 11, -4, 34]) [-4 34]))
    (is (= (t 31 [2, 8, 3, 9, 11, -4, 34]) [-4 34]))))

;;benchmarks
(do
  (time (wcc-01-filter 29 [2, 8, 3, 9, 11])) ;;"Elapsed time: 12.453969 msecs"
  (time (wcc-01-loop 29 [2, 8, 3, 9, 11])) ;;"Elapsed time: 1.33059 msecs"
  (time (t 29 [2, 8, 3, 9, 11]))) ;;"Elapsed time: 2.045518 msecs"
