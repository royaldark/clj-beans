(ns beans.game.player-test
  (:require [clojure.test :refer :all]
            [beans.game.card  :as c]
            [beans.game.field :as f]
            [beans.game.player :as p])
  (:import [java.lang IllegalArgumentException]))

(deftest new-player-test
  (testing "New player"
    (let [player (p/create "Joe")]
      (do (is (= "Joe" (p/name player)))
          (is (= 0 (p/gold player)))
          (is (= 0 (p/hand-size player)))
          (is (= [] (p/hand player)))))))

(deftest harvest-test
  (testing "Harvesting"
    (let [soy-field     (-> (f/create)
                          (f/add c/SOY_BEAN)
                          (f/add c/SOY_BEAN)
                          (f/add c/SOY_BEAN)
                          (f/add c/SOY_BEAN))
          player        (update-in (p/create "Joe") [:fields]
                          (fn [fields] (assoc fields 0 soy-field)))
          field          (p/field player 0)
          harvest-player (p/harvest player field)
          harvest-field  (p/field harvest-player 0)]
      (do (is (= 0 (p/gold player)))
          (is (= 2 (p/gold harvest-player)))

          (is (= 4 (f/size field)))
          (is (= 0 (f/size harvest-field)))))))
