(ns beans.game.game
  "Namespace for creating and using clj-beans Games. Games are immutable
   records which contain all current and prior game states."
  (:require [beans.game.deck       :as deck]
            [beans.game.game-state :as gs]))

(def ^:private INITIAL_DRAW 5)
(def ^:private TIMES_THROUGH_DECK 3)

(defrecord Game [states times-through-deck]
  Object
    (toString [game] (str "Game["
                          (->> game :states count) " states, "
                          "current: " (->> game :states first)
                          "]")))

(defn create
  "Creates a game with the given players. The game created is in an unstarted
   state."
  ([players] (create players TIMES_THROUGH_DECK))
  ([players times-through-deck]
    (let [initial-game-state (gs/map->GameState {:deck          (deck/create)
                                                 :discard       []
                                                 :players       players
                                                 :active-player (first players)
                                                 :phase         gs/PLANTING_PHASE})]
      (if (> 3 (count players))
        (throw (IllegalArgumentException. "Cannot create game with less than 3 players."))
        (Game. [initial-game-state] times-through-deck)))))

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
  (let [num-players (->> initial-game-state :players count)]
    (->> (range num-players)
      (reduce draw-initial-hand initial-game-state))))

(def start
  (modify-current-state draw-initial-hands))

(defn num-states [game]
  (->> game :states count))

(defn phase [game]
  (->> game :states first :phase))

(defn active-player [game]
  (->> game :states first :active-player))
