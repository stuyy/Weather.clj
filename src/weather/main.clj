(ns weather.main
  (:gen-class)
  (:require [weather.api :refer :all]))

(defn -main [& args]
  (println "Please enter your API key from openweathermap.org") ; make http get request to validate their key.
  (loop [api-key (read-line)]
    (if (= api-key "exit") 
      (println "Goodbye!")
      (do 
        (let [res (validate-key (str base-forecast base-city "&appid=" api-key))]
        (if (not= res nil) 
          (do 
            (println "Valid API KEY!") 
            (if (nil? @API_KEY) (reset! API_KEY api-key) nil) 
            (commands api-key))
          (do (println "Invalid API Key. Try again?") (recur (read-line)))))))))
