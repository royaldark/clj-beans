(ns beans.game.game-state)

(defrecord GameState [deck discard players])

(defn- player-index [gs player]
  (->> (:players gs)
    (some (partial = player))))

(defn modify-player [gs player modify-fn]
  (let [index (player-index gs player)]
    (if-not index
      (throw (Exception. "Player not in game."))
      (update-in gs
                 [:players index]
                 modify-fn))))

(defn player-modifier [modify-fn]
  (fn [gs player]
    (modify-player gs player modify-fn)))

(defn draw [gs player]
  (map->GameState {:deck    (->> gs :deck rest)
                   :discard (->> gs :discard)
                   :players (modify-player gs
                                           player
                                           (fn [player]
                                             ;(player/add-card
                                              (identity
                                               (->> gs :deck first))))}))
