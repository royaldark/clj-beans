(ns beans.game.player
  (:require clojure.pprint
            [beans.game.field :as field]))

(defrecord Player [name gold hand fields])

(defn create [name]
  (map->Player {:name   name
                :gold   0
                :hand   []
                :fields [(field/create)
                         (field/create)]}))
