(ns beans.game.game
  (:require [beans.game.deck       :as deck]
            [beans.game.game-state :as gs]))

(def INITIAL_DRAW 5)

(defrecord Game [states])

(defn create [players]
  (Game.
    [(gs/map->GameState {:deck    (deck/create)
                         :discard []
                         :players players})]))

(defn- modify-current-state [modify-fn]
  (fn [game & args]
    (let [modify-fn-args (concat [(->> game :states first)] args)]
      (apply modify-fn modify-fn-args))))

(def draw
  (modify-current-state gs/draw))

(defn- draw-initial [player]
  player)

(def start
  (modify-current-state
    (fn [state]
      (update-in state [:players]
        (partial map draw-initial)))))
