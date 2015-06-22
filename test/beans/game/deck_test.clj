(ns beans.game.deck-test
  (:require [clojure.test :refer :all]
            [beans.game.card :as c]
            [beans.game.deck :as d]))

(deftest basic-method-tests
  (testing "Basic Deck methods"
    (do (is (= 154 (d/size d/STANDARD_DECK)))
        (is (= 153 (d/size (d/rest d/STANDARD_DECK))))

        (is (instance? beans.game.card.Card (d/first d/STANDARD_DECK)))
        (is (instance? beans.game.deck.Deck (d/rest d/STANDARD_DECK)))

        (is (not= (d/shuffle d/STANDARD_DECK)
                  (d/shuffle d/STANDARD_DECK))))))

