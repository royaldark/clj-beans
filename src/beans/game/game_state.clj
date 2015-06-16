(ns beans.game.game-state
  (:require [beans.game.deck   :as d]
            [beans.game.player :as p]))

(defrecord GameState [deck discard players])

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
  (map->GameState {:deck    (->> gs :deck d/rest)
                   :discard (->> gs :discard)
                   :players (modify-player gs player modify-fn)})))
