(ns beats-playlist-export.core
  (:require [beats-clj.core :as beats])
  (:gen-class :main true))

(defn get-param [n d]
  (let [system_env (System/getenv n)
        system_prop (System/getProperty n)]
    (if-let [param (if system_env system_env system_prop)]
      param
      (do
        (println (str "Server " n " parameter not set! Defaulting to " d))
        d))))

(defn get-tracks [tracks]
  (map #(let [track-meta (try (beats/track-get (:id %)) (catch Exception e {}))]
          (assoc % :artist (get-in track-meta [:data :artist_display_name])
                   :title (get-in track-meta [:data :title]))) tracks))

(defn write-file [outfile playlists]
  (with-open [wtr (clojure.java.io/writer outfile :append true)]
    (doseq [p playlists]
      (println (:name p))
      (.write wtr (str "## " (:name p) "\n"))
      (doseq [track (get-in p [:refs :tracks])]
        (.write wtr (str (:title track) " - " (:artist track) "\n")))
      (.write wtr "\n\n"))))

(defn start [token & {:keys [outdir] :or {outdir "/tmp"}}]
  (let [me (beats/me :auth token)
        user-id (get-in me [:result :user_context])
        outfile (str outdir "/" user-id "_beats-playlists.txt")]
    (->> (beats/playlist-list user-id :auth token)
         :data
         (map #(assoc-in % [:refs :tracks] (get-tracks (get-in % [:refs :tracks]))))
         (write-file outfile))
    (println (str "Complete. Output File: " outfile))))

(defn -main
  [& args]
  (let [beats-api-token (get-param "BEATS_API_TOKEN" false)
        outdir (get-param "OUTDIR" "/tmp")]
    (beats/set-app-key! (get-param "BEATS_API_KEY" false))
    (beats/set-app-secret! (get-param "BEATS_API_SECRET" false))
    (if (empty? outdir)
        (println "Please specify your output directory via the OUTDIR param.")
        (start beats-api-token :outdir outdir))
    (shutdown-agents)))
