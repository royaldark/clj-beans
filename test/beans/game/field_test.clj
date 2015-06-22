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

(defn- add-cards [field card quantity]
  (->> field
    (iterate #(f/add % card))
    (take (inc quantity))
    (last)))

(deftest field-value-test
  (testing "Field Value:"
    (let [empty-field     (f/create)
          chili-field     (f/add empty-field c/CHILI_BEAN)
          big-chili-field (add-cards empty-field c/CHILI_BEAN 6)]

      (testing "empty = 0"
        (is (= 0 (f/value empty-field))))

      (testing "others = right value"
        (do (is (= 0 (f/value chili-field)))
            (is (= 2 (f/value big-chili-field)))))

      (testing "fullness calculation"
        (let [empty-field (f/create)]
          (do (is (false? (f/full? empty-field)))

              (is (false? (f/full?
                            (add-cards empty-field c/COCOA_BEAN 3))))
              (is (true?  (f/full?
                            (add-cards empty-field c/COCOA_BEAN 4))))

              (is (false? (f/full?
                            (add-cards empty-field c/COFFEE_BEAN 11))))
              (is (true?  (f/full?
                            (add-cards empty-field c/COFFEE_BEAN 12))))))))))
