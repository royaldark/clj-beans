(ns beans.game.game-test
  (:require [clojure.test :refer :all]
            [beans.game.deck       :as d]
            [beans.game.game-state :as gs]
            [beans.game.game       :as g]
            [beans.game.player     :as p])
  (:import [java.lang IllegalArgumentException]))

(deftest initial-draw-test
  (testing "Initial draw:"
    (let [player-1     (p/create "Joe")
          player-2     (p/create "Brian")
          game         (g/create [player-1 player-2])
          started-game (g/start game)]

      (do (is (= 1 (g/num-states game)))
          (is (= 2 (g/num-states started-game)))

          (is (= gs/PLANTING_PHASE
                 (g/phase game)
                 (g/phase started-game)))

          (is (= player-1
                 (g/active-player game)
                 (g/active-player started-game)))))))
