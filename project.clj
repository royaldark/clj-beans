(defproject beans "0.1.0-SNAPSHOT"
  :description "Clojure Beans"
  :url "https://github.com/royaldark"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :main ^:skip-aot beans.core
  :target-path "target/%s"
  :profiles {:dev {:plugins [[com.jakemccrary/lein-test-refresh "0.9.0"]]}
  	         :uberjar {:aot :all}})
