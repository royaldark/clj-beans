(ns beans.game.deck-test
  (:require [clojure.test :refer :all]
            [beans.game.deck       :as d])
  (:import [java.lang IllegalArgumentException]))

(deftest standard-deck-test
  (testing "Standard deck"
    (is (= 154 (d/size d/STANDARD_DECK)))))

