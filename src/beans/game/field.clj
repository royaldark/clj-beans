(ns beans.game.field
  "Namespace for creating and manipulating Fields. Fields are immutable records
   which represent a player's field they can play into."
  (:require [beans.game.card :as c])
  (:import [java.lang IllegalArgumentException]))

(defrecord Field [cards]
  Object
  (toString [field]
    (str "Field["
      (count (:cards field))
      " x "
      (c/name (first (:cards field)))
      "]")))

(defn create
  "Creates and returns a new Field record."
  []
  (Field. []))

(defn size
  "Returns the number of cards in `field`."
  [field]
  (->> field :cards count))

(defn add
  "Returns a new Field with `new-card` appended."
  [field new-card]
  (let [top-card (->> field :cards first)]
  (if (or (nil? top-card)
          (= top-card new-card))
    (update-in field [:cards] #(conj % new-card))
    (throw (IllegalArgumentException.
             (str "Can't add " new-card " to stack of " top-card))))))

(defn value
  "Returns the value in gold of the cards in `field`."
  [field]
  (let [cards (:cards field)]
    (c/value (first cards) (count cards))))

(defn- max-harvest-for
  "Returns the highest non-nil value in the Card's harvest info"
  [card]
  (if (nil? card)
    0
    (->> (:harvests card)
         (remove nil?)
         (last))))

(defn full?
  "Returns true if the number of Cards in the Field >= the maximum harvest for
   that Card type, false otherwise."
  [field]
  (let [cards       (:cards field)
        max-harvest (max-harvest-for (first cards))]
    (>= (size field) max-harvest)))
