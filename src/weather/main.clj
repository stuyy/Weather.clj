(ns weather.main)

(defn -main [& args]
  (println "Please enter your API key from openweathermap.org")
  (let [api-key (read-line)]
    (println (str "Your API key: " api-key)))
)