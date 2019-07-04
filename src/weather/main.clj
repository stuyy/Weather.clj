(ns weather.main
  (:require [clj-http.client :as client]))

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
        (let [cmd-args (list cmd)]
          (println cmd-args)
          (cond
            (= (first cmd-args) "weather") (println "Making weather call!")
            (= (first cmd-args) "forecast") (println "Making forecast call!")))
          (println "Enter a command")
          (recur (read-line)))    )))

(defn -main [& args]
  ; make http get request to validate their key.
  (println "Please enter your API key from openweathermap.org")
  (loop [api-key (read-line)]
    (if (= api-key "exit") 
      (println "Goodbye!")
      (do
        (let [res (validate-key (str base-url base-city "&appid=" api-key))]
          (if (not= res nil) 
            (do
              (println "Valid API KEY!")
              (commands))
            (do 
              (println "Invalid API Key. Try again?") 
              (recur (read-line)))))))))
