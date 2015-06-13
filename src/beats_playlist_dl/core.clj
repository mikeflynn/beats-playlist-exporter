(ns beats-playlist-dl.core
  (:require [beats-clj.core :as beats]))

(beats/set-app-key! "REPLACE ME!!!!")
(beats/set-app-secret! "REPLACE ME!!!!")

(defn get-tracks [tracks]
  (map #(let [track-meta (beats/track-get (:id %))]
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