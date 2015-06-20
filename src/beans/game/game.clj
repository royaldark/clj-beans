(ns beans.game.game
  (:require [beans.game.deck       :as deck]
            [beans.game.game-state :as gs]))

(def INITIAL_DRAW 5)

(defrecord Game [states]
  Object
    (toString [game] (str "Game["
                          (->> game :states count) " states, "
                          "current: " (->> game :states first)
                          "]")))

(defn create [players]
  (Game.
    [(gs/map->GameState {:deck    (deck/create)
                         :discard []
                         :players players})]))

(defn- modify-current-state [modify-fn]
  (fn [game & args]
    (let [modify-fn-args (concat [(->> game :states first)] args)]
      (update-in game [:states]
        #(conj % (apply modify-fn modify-fn-args))))))

(def draw
  (modify-current-state gs/draw))

(defn- draw-initial-hand [game-state idx]
  (->> game-state
    (iterate #(gs/draw % (gs/player % idx)))
    (take 6)
    (last)))

(defn- draw-initial-hands [initial-game-state]
  (let [num-players (->> initial-game-state :players count)
    _ (println num-players)]
    (->> (range num-players)
      (reduce draw-initial-hand initial-game-state))))

(def start
  (modify-current-state draw-initial-hands))

(defn num-states [game]
  (->> game :states count))
