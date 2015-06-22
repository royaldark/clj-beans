(ns beans.game.deck
  "Namespace for creating and manipulating Deck records. The \"typical\" full
   deck is exposed as STANDARD_DECK, but the `create` method is preferred. For
   decks other than the standard, try the lower-level `build-deck` method."
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
  "The full set of standard cards, unshuffled and unadjusted for the number of
   players. It is recommended Decks are created via the `create` method instead"
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
(defn size
  "Returns the number of cards in the Deck"
  [deck]
  (count (:cards deck)))

(defn first
  "Returns the top Card in the Deck"
  [deck]
  (clojure.core/first (:cards deck)))

(defn rest
  "Returns a new Deck with all Cards except the top"
  [deck]
  (update-in deck [:cards] clojure.core/rest))

(defn shuffle
  "Returns a new Deck where all the Cards have been shuffled"
  [deck]
  (update-in deck [:cards] clojure.core/shuffle))

(defn create
  "The preferred way to generate Decks. Returns the STANDARD_DECK shuffled."
  []
  (shuffle STANDARD_DECK))
