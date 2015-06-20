(ns beans.game.deck
  "Namespace for creating and manipulating Deck records. The \"typical\" full
   deck is exposed as STANDARD_DECK, but custom decks may be built using the
   build-deck function."
  (:refer-clojure :exclude [shuffle first rest])
  (:require [beans.game.card :as card]))

(defrecord Deck [cards])

(defn build-deck 
  "Builds a Deck record out of a map {k v} where k is a Card record and v is
   the quantity."
  ([card-map]
    (->> card-map
      (mapcat (fn [[card quantity]]
                (repeat quantity card)))
      (into [])
      (Deck.))))

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

(defn shuffle [deck]
  (->> (:cards deck)
    (clojure.core/shuffle)
    (Deck.)))

(defn first [deck]
  (->> (:cards deck)
    (clojure.core/first)
    (Deck.)))

(defn rest [deck]
  (->> (:cards deck)
    (clojure.core/rest)
    (Deck.)))

(defn create []
  (shuffle STANDARD_DECK))

(defn size [deck]
  (->> deck :cards count))