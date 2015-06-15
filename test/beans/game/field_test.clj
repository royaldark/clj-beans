(ns beans.game.field-test
  (:require [clojure.test :refer :all]
            [beans.game.card  :as c]
            [beans.game.field :as f])
  (:import [java.lang IllegalArgumentException]))

(deftest new-field-test
  (testing "New Fields"
    (let [field (f/create)]
      (testing "have 0 cards"
        (is (= 0 (f/size field)))))))

(deftest field-add-test
  (testing "Adding to Fields:"
    (let [empty-field (f/create)
          chili-field (f/add empty-field c/CHILI_BEAN)]
      (testing "increases size"
        (do (is (= 1 (f/size chili-field)))
            (is (= 2 (f/size (f/add chili-field c/CHILI_BEAN))))))

      (testing "disallows mixing of types"
        (is (thrown? IllegalArgumentException
                     (f/add chili-field c/BLUE_BEAN)))))))
