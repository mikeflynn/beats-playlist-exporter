(defproject beats-playlist-export "0.1"
  :description "A Beats Music Playlist Exporter"
  :url "http://github.com/mikeflynn/beats-playlist-export"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main beats-playlist-export.core
  :aot :all
  :uberjar-name "beats-playlist-export.uber.jar"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [beats-clj "0.9.5"]])
