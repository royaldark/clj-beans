(ns beans.game.player
  (:refer-clojure :exclude [name])
  (:require clojure.pprint
            [beans.game.field :as f]))

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
                :fields [(f/create)
                         (f/create)]}))

(defn- field-exists [player field]
  (some #{field} (:fields player)))

(defn- replace-item [coll item replacement]
  (map #(if (= item %) replacement item) coll))

(defn name
  "Returns a Player's name."
  [player]
  (:name player))

(defn gold
  "Returns a Player's gold quantity."
  [player]
  (:gold player))

(defn field
  "Returns a Player's field at `idx`."
  [player idx]
  (-> (:fields player)
      (nth idx)))

(defn hand
  "Returns a collection of all cards in a Player's hand."
  [player]
  (:hand player))

(defn hand-size
  "Returns the number of cards in a Player's hand."
  [player]
  (count (hand player)))

(defn add-card
  "Returns a new Player with `card` appended to their hand."
  [player card]
  (update-in player [:hand] #(conj % card)))

(defn harvest
  "Returns a new Player record where `field` has been havested."
  [player field]
  (if-not (field-exists player field)
    (throw (IllegalArgumentException. "Invalid field to harvest."))
    (let [gold-earned (f/value field)
          new-field   (f/create)]
      (-> player
        (update-in [:gold] (partial + gold-earned))
        (update-in [:fields] #(replace-item % field new-field))))))
