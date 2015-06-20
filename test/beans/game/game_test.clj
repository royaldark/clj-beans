(ns beans.game.game-test
  (:require [clojure.test :refer :all]
            [beans.game.deck       :as d]
            [beans.game.game-state :as gs]
            [beans.game.game       :as g]
            [beans.game.player     :as p])
  (:import [java.lang IllegalArgumentException]))

(deftest initial-draw-test
  (testing "Initial draw:"
    (let [game         (g/create [(p/create "Joe")
                                  (p/create "Brian")])
          started-game (g/start game)]
      (do (is (= 1 (g/num-states game)))
          (is (= 2 (g/num-states started-game)))))))
