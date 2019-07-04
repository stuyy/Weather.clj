(ns weather.api
  (:require [clj-http.client :as client])
  (:require [clojure.string :as str])
  (:require [cheshire.core :refer :all]))

(def API_KEY (atom nil))
(def base-city "london,uk")
(def base-forecast "https://api.openweathermap.org/data/2.5/forecast?q=")
(def base-weather "https://api.openweathermap.org/data/2.5/weather?q=")
(def current-weather (atom nil))
(def current-forecast (atom nil))

(def menu [
  '("weather" "city-name" "gets the current forecast for given city")
  '("forecast" "city-name" "gets the forecast for given city")])

(defn validate-key [url]
  (println (str "Making HTTP/GET request to " url))
  (try 
    (let [res (client/get url)] true)
    (catch Exception err (println (.getMessage err)) nil)))

(defn display-menu []
  (println "Enter a command followed by its parameters to get started!")
  (println "----------------------------------------------------------")
  (loop [m menu]
    (if (empty? m) nil (do (println (first m)) (recur (rest m))))))

(defn get-weather [city key]
  (try
    (let [res (:body (client/get (str base-weather (str/join " " city) "&appid=" key)))]
      (let [weather-map (parse-string res)]

        (println (get weather-map "weather"))
        (println (keys weather-map)))
    )
  (catch Exception err (println (.getMessage err))))
)

(defn commands [key]
  (display-menu) ; display menu for user.
  (println "Enter a command")
  (loop [cmd (read-line)]
    (if (= cmd "exit") (println "Goodbye!")
      (do
        (let [cmd-args (str/split cmd #" ")]
          (println cmd-args)
          (cond
            (= (first cmd-args) "weather") (get-weather (rest cmd-args) key)
            (= (first cmd-args) "forecast") (println "Making forecast call!")))
        (println "Enter a command")
        (recur (read-line))))))