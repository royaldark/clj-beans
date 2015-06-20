(ns beans.game.game-state
  "Namespace for creating and manipulating GameStates. GameStates are immutable
   records which are a snapshot of a specific point of time in a game."
  (:require [beans.game.deck   :as d]
            [beans.game.player :as p]))

(def PLANTING_PHASE 0)
(def TRADING_PHASE 1)
(def POST_TRADING_PHASE 2)
(def DRAW_PHASE 3)

(defrecord GameState [deck discard players active-player phase]
  Object
   (toString [gs] (str "GameState["
                       "Deck: " (->> gs :deck d/size) " cards remaining, "
                       "Discard: " (->> gs :discard count) " cards, "
                       "Players: " (clojure.string/join ", " (:players gs))
                       "]")))

(defn- player-index [gs player]
  (.indexOf (:players gs) player))

(defn player [gs idx]
  (nth (:players gs) idx))

(defn modify-player [gs player modify-fn]
  (let [players (:players gs)
        index   (player-index gs player)]
    (if-not index
      (throw (Exception. "Player not in game."))
      (assoc players index
                     (modify-fn player)))))

(defn draw [gs player]
  (let [modify-fn (fn [player]
                    (p/add-card player (->> gs :deck d/first)))]
  (map->GameState {:deck          (->> gs :deck d/rest)
                   :discard       (->> gs :discard)
                   :players       (modify-player gs player modify-fn)
                   :active-player (->> gs :active-player)
                   :phase         (->> gs :phase)})))
