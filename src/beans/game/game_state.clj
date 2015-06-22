(ns beans.game.game-state
  "Namespace for creating and manipulating GameStates. GameStates are immutable
   records which are a snapshot of a specific point of time in a game."
  (:require [beans.game.deck   :as d]
            [beans.game.player :as p]))

(def ^:private phase-counter (atom 0))
(defn- new-phase []
  (let [id @phase-counter]
    (swap! phase-counter inc)
    id))

(def PLANTING_PHASE (new-phase))
(def TRADING_PHASE (new-phase))
(def POST_TRADING_PHASE (new-phase))
(def DRAW_PHASE (new-phase))

(def ^:private NUM_PHASES @phase-counter)

(defrecord GameState [deck discard players active-player phase]
  Object
   (toString [gs] (str "GameState["
                       "Deck: " (-> gs :deck d/size) " cards remaining, "
                       "Discard: " (-> gs :discard count) " cards, "
                       "Players: " (clojure.string/join ", " (:players gs)) ", "
                       "Active Player: " (:active-player gs) ", "
                       "Phase: " (:phase gs)
                       "]")))

(defn- player-index [gs player]
  (.indexOf (:players gs) player))

(defn player
  "Fetches Player at `idx` in GameState `gs`."
  [gs idx]
  (nth (:players gs) idx))

(defn modify-player
  "Given a GameState `gs` and function `modify-fn`, modifies Player `player`
   within that GameState to have new value (modify-fn player)."
  [gs player modify-fn]
  (let [players (:players gs)
        index   (player-index gs player)]
    (if-not index
      (throw (Exception. "Player not in game."))
      (assoc players index
                     (modify-fn player)))))

(defn draw
  "Returns a new GameState where Player `player` has drawn a single card from
   the draw pile."
  [gs player]
  (let [modify-fn (fn [player]
                    (p/add-card player (->> gs :deck d/first)))]
    (merge gs {:deck    (-> gs :deck d/rest)
               :players (-> gs (modify-player player modify-fn))})))

(defn harvest
  "Returns a new GameState where `player` has harvested field `field`."
  [gs player field]
  (assoc gs :players (modify-player player #(p/harvest % field))))

(defn next-phase
  "Returns a new GameState in the next phase of the current turn."
  [gs]
  (if (>= (:phase gs) (dec NUM_PHASES))
    (throw (IllegalArgumentException. "Cannot advance past last phase."))
    (update-in gs [:phase] inc)))

