(ns beans.game.game-test
  (:require [clojure.test :refer :all]
            [beans.game.deck       :as d]
            [beans.game.game-state :as gs]
            [beans.game.game       :as g]
            [beans.game.player     :as p])
  (:import [java.lang IllegalArgumentException]))

(def player-1 (p/create "Joe"))
(def player-2 (p/create "Brian"))
(def player-3 (p/create "Dizzle"))

(deftest bad-args-test
  (testing "Bad constructor args:"
    (testing "# players"
      (do (is (thrown? IllegalArgumentException (g/create [])))
          (is (thrown? IllegalArgumentException (g/create [player-1])))
          (is (thrown? IllegalArgumentException (g/create [player-1 player-2])))))))

(deftest initial-draw-test
  (testing "Initial draw:"
    (let [game         (g/create [player-1 player-2 player-3])
          started-game (g/start game)]

      (do (is (= 1 (g/num-states game)))
          (is (= 2 (g/num-states started-game)))

          (is (= gs/PLANTING_PHASE
                 (g/phase game)
                 (g/phase started-game)))

          (is (= player-1
                 (g/active-player game)
                 (g/active-player started-game)))))))
