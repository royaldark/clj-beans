(ns beans.game.deck
  (:require [beans.game.card :as card]))

(defrecord Deck [cards])

(defn- build-deck [card-map]
  (->> card-map
    (mapcat (fn [[card quantity]]
              (repeat quantity card)))
    (into [])))

(def STANDARD_DECK
  (build-deck {card/COFFEE_BEAN     24
               card/WAX_BEAN        22
               card/BLUE_BEAN       20
               card/CHILI_BEAN      18
               card/STINK_BEAN      16
               card/GREEN_BEAN      14
               card/SOY_BEAN        12
               card/BLACK_EYED_BEAN 10
               card/RED_BEAN        8
               card/GARDEN_BEAN     6
               card/COCOA_BEAN      4}))

(defn create []
  (shuffle STANDARD_DECK))
