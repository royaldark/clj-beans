(ns beans.core
  (:require [beans.game.deck   :as deck]
            [beans.game.game   :as game]
            [beans.game.player :as player])
  (:gen-class))

(defn -main [& args]
  (clojure.pprint/pprint (game/create
                           [(player/create "Joey")
                            (player/create "Dizzle")
                            (player/create "Brian")])))
