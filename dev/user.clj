(ns user
  (:use [figwheel-sidecar.repl-api :as ra]))

(defn start [& args] (apply ra/start-figwheel! args))

(defn stop [] (ra/stop-figwheel!))

(defn cljs [] (ra/cljs-repl "dev"))

