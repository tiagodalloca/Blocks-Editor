(ns blocks-editor.utils
  (:require   
   [cljs.core.async :as async
    :refer [put! chan tap mult <! >! timeout close!]])
  (:require-macros [cljs.core.async.macros
                    :refer [go go-loop alt!]]))

(defonce node-require (js* "require"))
(defonce fs (node-require "fs"))
(defonce spawn (-> "child_process" node-require .-spawn))

(defn warn [s] (.warn js/console s))

(defn read-file [path]
  "Returns a channel containing the return"
  (let [c (chan)]
    (.readFile fs path "utf8" (fn [err data]
                                (go (>! c (if err
                                            (warn err) data)))))
    c))

(defn read-dir [dir]
  (let [c (chan)]
    (.readdir fs dir (fn [err files]
                       (go (>! c (if err
                                   (warn err) (js->clj files))))))
    c))

;; COMPILER STUFF

(defn call-system [cmd args]
  (let [child (spawn cmd args)
        c (-> 1 async/buffer chan)]
    (-> child -.stdout (.on "data" (fn [data] (>! c [:out data]))))
    (-> child -.stderr (.on "data" (fn [data] (>! c [:err data]))))
    (-> child (.on "exit" (fn [code] (>! c [:exit code]))))
    c))

