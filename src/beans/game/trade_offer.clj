(ns beans.game.trade-offer
  "Namespace for creating and manipulating TradeOffers. TradeOffers are
   records which represent a trade proposal from one player to another."
  (:require [beans.game.deck   :as d]
            [beans.game.player :as p]))

(defrecord TradeOffer [player-a player-a-cards player-b player-b-cards state]
	Object
  (toString [gs]
    (str "TradeOffer["
      "Player A: " (-> gs :player-a) ","
      "Cards, A -> B: " (-> gs :player-a-cards) "," 
      "Player B: " (-> gs :player-b) ","
      "Cards, B -> A: " (-> gs :player-b-cards) "," 
      "]")))

(defn create [input]
  (map->TradeOffer
    (assoc input :state :open)))

(defn accept [to]
  (assoc to :state :accepted))

(defn reject [to]
  (assoc to :state :rejected))
