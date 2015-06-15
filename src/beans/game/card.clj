(ns beans.game.card)

(defrecord Card [name harvests])

(def COFFEE_BEAN
  (map->Card {:name     "Coffee Bean"
              :harvests [4 7 10 12]}))

(def WAX_BEAN
  (map->Card {:name     "Wax Bean"
              :harvests [4 7 9 11]}))

(def BLUE_BEAN
  (map->Card {:name     "Blue Bean"
              :harvests [4 6 8 10]}))

(def CHILI_BEAN
  (map->Card {:name     "Chili Bean"
              :harvests [3 6 8 9]}))

(def STINK_BEAN
  (map->Card {:name     "Stink Bean"
              :harvests [3 5 7 8]}))

(def GREEN_BEAN
  (map->Card {:name     "Green Bean"
              :harvests [3 5 6 7]}))

(def SOY_BEAN
  (map->Card {:name     "Soy Bean"
              :harvests [2 4 6 7]}))

(def BLACK_EYED_BEAN
  (map->Card {:name     "Black-eyed Bean"
              :harvests [2 4 5 6]}))

(def RED_BEAN
  (map->Card {:name     "Red Bean"
              :harvests [2 3 4 5]}))

(def GARDEN_BEAN
  (map->Card {:name     "Coffee Bean"
              :harvests [nil 2 3 nil]}))

(def COCOA_BEAN
  (map->Card {:name     "Cocoa Bean"
              :harvests [nil 2 3 4]}))

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
