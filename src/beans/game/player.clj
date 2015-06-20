(ns beans.game.player
  (:require clojure.pprint
            [beans.game.field :as field]))

(defrecord Player [name gold hand fields]
  Object
    (toString [player] (str "Player["
                            "Name: " (:name player) ", "
                            "Gold: " (:gold player) ", "
                            "Hand: " (->> player :hand count) " cards, "
                            "Fields: " (->> player :fields count)
                            "]")))

(defn create [name]
  (map->Player {:name   name
                :gold   0
                :hand   []
                :fields [(field/create)
                         (field/create)]}))

(defn hand-size [player]
  (->> player :hand count))

(defn add-card [player card]
  (update-in player [:hand] #(conj % card)))
