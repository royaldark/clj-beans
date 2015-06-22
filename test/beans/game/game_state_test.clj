(ns beans.game.game-state-test
  (:require [clojure.test :refer :all]
            [beans.game.deck       :as d]
            [beans.game.game-state :as gs]
            [beans.game.player     :as p])
  (:import [java.lang IllegalArgumentException]))

(defn empty-game-state []
  (let [players [(p/create "Joe") (p/create "Bob")]]
    (gs/map->GameState {:players       players
                        :active-player (first players)
                        :phase         gs/PLANTING_PHASE
                        :discard       []
                        :deck          (d/create)})))

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

(deftest phase-test
  (testing "GameState phase transitions:"
    (let [phase-1 (empty-game-state)
          phase-2 (gs/next-phase phase-1)
          phase-3 (gs/next-phase phase-2)
          phase-4 (gs/next-phase phase-3)]

    (testing "valid transitions"
      (do (is (< (:phase phase-1)
                 (:phase phase-2)
                 (:phase phase-3)
                 (:phase phase-4)))

          (is (= (:active-player phase-1)
                 (:active-player phase-2)
                 (:active-player phase-3)
                 (:active-player phase-4)))))

    (testing "invalid transitions"
      (is (thrown? IllegalArgumentException (gs/next-phase phase-4)))))))

(deftest turn-test
  (testing "GameState turn transitions:"
    (let [phase-1 (empty-game-state)
          phase-2 (gs/next-phase phase-1)
          phase-3 (gs/next-phase phase-2)
          phase-4 (gs/next-phase phase-3)]

    (testing "valid transitions"
      (let [new-turn (gs/next-turn phase-4)]
        (do (is (not= (:active-player phase-4)
                      (:active-player new-turn)))

            (is (not= (:phase phase-4)
                      (:phase new-turn))))))

    (testing "invalid transitions"
      (do (is (thrown? IllegalArgumentException (gs/next-turn phase-1)))
          (is (thrown? IllegalArgumentException (gs/next-turn phase-2)))
          (is (thrown? IllegalArgumentException (gs/next-turn phase-3))))))))
