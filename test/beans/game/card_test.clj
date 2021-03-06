(ns beans.game.card-test
  (:require [clojure.test :refer :all]
            [beans.game.card  :as c]
            [beans.game.field :as f])
  (:import [java.lang IllegalArgumentException]))

(deftest create-card-test
  (testing "Card creation"
    (let [weird-bean (c/create "Weird Bean" [1 2 3 4])]
      (do (is (= "Weird Bean" (c/name weird-bean))
          (is (= [1 2 3 4]    (c/harvests weird-bean))))))))

(deftest card-value-test
  (testing "Card values:"
    (testing "calculate correctly"
      (do (is (= 2 (c/value c/SOY_BEAN 4)))
          (is (= 2 (c/value c/SOY_BEAN 5)))
          (is (= 3 (c/value c/SOY_BEAN 6)))
          (is (= 4 (c/value c/SOY_BEAN 7)))

          (is (= 0 (c/value c/GARDEN_BEAN 0)))
          (is (= 0 (c/value c/GARDEN_BEAN 1)))
          (is (= 2 (c/value c/GARDEN_BEAN 2)))
          (is (= 3 (c/value c/GARDEN_BEAN 3)))
          (is (= 3 (c/value c/GARDEN_BEAN 4)))))))
