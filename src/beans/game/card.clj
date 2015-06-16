(ns beans.game.card
  (:refer-clojure :exclude [name]))

(defrecord Card [name harvests])

(defn value [card quantity]
  (let [values (into (sorted-map)
                     (zipmap [1 2 3 4] (:harvests card)))]
    (->> values
      (reduce-kv (fn [result gold needed]
                   (if (and (not (nil? needed))
                            (>= quantity needed))
                     gold
                     result))
                  0))))

(defn create [name harvests]
  (map->Card {:name     name
              :harvests harvests}))

(defn name [card]
  (:name card))

(defn harvests [card]
  (:harvests card))

(def COFFEE_BEAN
  (create "Coffee Bean" [4 7 10 12]))

(def WAX_BEAN
  (create "Wax Bean" [4 7 9 11]))

(def BLUE_BEAN
  (create "Blue Bean" [4 6 8 10]))

(def CHILI_BEAN
  (create "Chili Bean" [3 6 8 9]))

(def STINK_BEAN
  (create "Stink Bean" [3 5 7 8]))

(def GREEN_BEAN
  (create "Green Bean" [3 5 6 7]))

(def SOY_BEAN
  (create "Soy Bean" [2 4 6 7]))

(def BLACK_EYED_BEAN
  (create "Black-eyed Bean" [2 4 5 6]))

(def RED_BEAN
  (create "Red Bean" [2 3 4 5]))

(def GARDEN_BEAN
  (create "Coffee Bean" [nil 2 3 nil]))

(def COCOA_BEAN
  (create "Cocoa Bean" [nil 2 3 4]))
