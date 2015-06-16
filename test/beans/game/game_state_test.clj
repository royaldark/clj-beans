(ns beans.game.game-state-test
  (:require [clojure.test :refer :all]
            [beans.game.deck       :as d]
            [beans.game.game-state :as gs]
            [beans.game.player     :as p])
  (:import [java.lang IllegalArgumentException]))

(defn empty-game-state []
  (gs/map->GameState {:players [(p/create "Joe") (p/create "Bob")]
                      :discard []
                      :deck    (d/create)}))

(deftest player-draw-test
  (testing "Player draw"
    (let [old-state       (empty-game-state)
          old-draw-player (gs/player old-state 0)
          new-state       (gs/draw old-state old-draw-player)
          new-draw-player (gs/player new-state 0)]
      (do (is (= 1
                 (- (->> old-state :deck d/size)
                    (->> new-state :deck d/size))))

          (is (= (->> old-state :discard)
                 (->> new-state :discard)))

          (is (= 1
                 (- (->> new-draw-player p/hand-size)
                    (->> old-draw-player p/hand-size))))))))

