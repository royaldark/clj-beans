(ns beans.game.field
  (:require [beans.game.card :as c])
  (:import [java.lang IllegalArgumentException]))

(defrecord Field [cards])

(defn create []
  (Field. []))

(defn size [field]
  (->> field :cards count))

(defn add [field new-card]
  (let [top-card (->> field :cards first)]
  (if (or (nil? top-card)
          (= top-card new-card))
    (update-in field [:cards] #(conj % new-card))
    (throw (IllegalArgumentException.
             (str "Can't add " new-card " to stack of " top-card))))))

(defn value [field]
  (let [cards (:cards field)]
    (c/value (first cards) (count cards))))
