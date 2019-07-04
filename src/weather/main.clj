(ns weather.main
  (:require [clj-http.client :as client]))

(def base-url "https://api.openweathermap.org/data/2.5/forecast?q=London,us&appid=")
(def menu [
  '("current" "city-name" "gets the current forecast for given city")
  '("forecast" "city-name" "gets the forecast for given city")])

(defn validate-key [endpoint-url]
  (println (str "Making HTTP/GET request to " endpoint-url))
  (let [url endpoint-url]
    (def response (client/get url {:async? true}
      (fn [res] (println res))
      (fn [ex] (println "exception: " (.getMessage ex)))))))

(defn display-menu []
  (println "Enter a command followed by its parameters to get started!")
  (println "----------------------------------------------------------")
  (loop [m menu]
    (if (empty? m) nil (do (println (first m)) (recur (rest m))))))

(defn -main [& args]
  (println "Please enter your API key from openweathermap.org")
  ; make http get request to validate their key.
  (let [api-key (read-line)]
    (validate-key (str base-url api-key))
    (println (str "Your API key: " api-key))
    (display-menu) ; display menu for user.
    
    (loop [cmd (read-line)]
      (if (= cmd "exit") (println "Goodbye!") (recur (read-line)))
    )
))
