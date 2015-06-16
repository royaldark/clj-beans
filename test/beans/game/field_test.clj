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

(deftest field-value-test
  (testing "Field Value:"
    (let [empty-field (f/create)
          chili-field (f/add empty-field c/CHILI_BEAN)
          big-chili-field (->> empty-field
                            (iterate #(f/add % c/CHILI_BEAN))
                            (take 7)
                            (last))]

      (testing "empty = 0"
        (is (= 0 (f/value empty-field))))

      (testing "others = right value"
        (do (is (= 0 (f/value chili-field)))
            (is (= 2 (f/value big-chili-field))))))))
