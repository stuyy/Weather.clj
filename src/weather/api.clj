(ns weather.api
  (:require [clj-http.client :as client])
  (:require [clojure.string :as str]))

(def base-city "london,uk")
(def base-url "https://api.openweathermap.org/data/2.5/forecast?q=")
(def menu [
  '("weather" "city-name" "gets the current forecast for given city")
  '("forecast" "city-name" "gets the forecast for given city")])

(defn validate-key [url]
  (println (str "Making HTTP/GET request to " url))
  (try 
    (let [res (client/get url)]
      (:body res))
    (catch Exception err
      (println (.getMessage err)) nil)))

(defn display-menu []
  (println "Enter a command followed by its parameters to get started!")
  (println "----------------------------------------------------------")
  (loop [m menu]
    (if (empty? m) nil (do (println (first m)) (recur (rest m))))))

(defn commands []
  (display-menu) ; display menu for user.
  (println "Enter a command")
  (loop [cmd (read-line)]
    (if (= cmd "exit") (println "Goodbye!")
      (do
        (let [cmd-args (str/split cmd #" ")]
          (println cmd-args)
          (cond
            (= (first cmd-args) "weather") (println "Making weather call!")
            (= (first cmd-args) "forecast") (println "Making forecast call!")))
        (println "Enter a command")
        (recur (read-line))))))