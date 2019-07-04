(ns weather.main)

(def menu [
  '("current" "city-name" "gets the current forecast for given city")
  '("forecast" "city-name" "gets the forecast for given city")
])
(defn display-menu []
  (println "Enter a command followed by its parameters to get started!")
  (println "----------------------------------------------------------")

  (loop [m menu]
    (if (empty? m) nil (do (println (first m)) (recur (rest m))))))

(defn -main [& args]
  (println "Please enter your API key from openweathermap.org")

  (let [api-key (read-line)]
    (println (str "Your API key: " api-key))
    (display-menu) ; display menu for user.

    (loop [cmd (read-line)]
      (if (= cmd "exit") (println "Goodbye!") (recur (read-line)))
    )
))
