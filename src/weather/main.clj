(ns weather.main
  (:require [weather.api :refer :all]))

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
