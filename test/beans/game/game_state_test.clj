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
  (testing "Player draw:"
    (testing "single draw"
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
                      (->> old-draw-player p/hand-size)))))))

    (testing "multiple draws"
      (let [old-state (empty-game-state)
            mid-state (->> old-state
                        (iterate #(gs/draw % (gs/player % 0)))
                        (take 6)
                        (last))
            new-state (->> mid-state
                        (iterate #(gs/draw % (gs/player % 1)))
                        (take 6)
                        (last))]
            
        (do (is (= 144 (->> new-state :deck d/size)))
            (is (= 5 (->> (gs/player new-state 0) p/hand-size)))
            (is (= 5 (->> (gs/player new-state 1) p/hand-size))))))))

