(ns blocks-editor.utils
  (:require   
   [cljs.core.async :as async
    :refer [put! chan tap mult <! >! timeout close!]])
  (:require-macros [cljs.core.async.macros
                    :refer [go go-loop alt!]]))

(defonce node-require (js* "require"))
(defonce fs (node-require "fs"))
(defonce exec (-> "child_process" node-require .-exec))
(defonce execFile (-> "child_process" node-require .-execFile))
(defonce dialog (-> "electron" node-require .-remote .-dialog))

(defn warn [s] (.warn js/console s))

(defn read-file [path]
  "Returns a channel containing the file content"
  (let [c (chan)]
    (.readFile fs path "utf8" (fn [err data]
                                (go (>! c (if err
                                            (warn err) data)))))
    c))

(defn select-save-file []
  "Returns a channel containing a file name"
  (let [c (chan)]
    (.showSaveDialog dialog #(go (when % (>! c %))))
    c))

(defn select-open-file []
  "Returns a channel containing a file name"
  (let [c (chan)]
    (.showOpenDialog dialog
                     #js  {:properties #js ["openFile"]}
                     #(go (when % (>! c %))))
    c))

(defn select-dir []
  "Returns a channel containing a file name"
  (let [c (chan)]
    (.showOpenDialog dialog #js {:properties #js ["openDirectory"]}
                     #(go (when % (>! c %))))
    c))

(defn save-to-file [path content]
  (let [c (chan)]
    (.writeFile fs path content (fn [err] (>! c err)))))

(defn delete [path]
  (let [c (chan)]
    (.unlink fs path (fn [err] (>! c err)))))

(defn read-dir [dir]
  (let [c (chan)]
    (.readdir fs dir (fn [err files]
                       (go (>! c (if err
                                   (warn err) (js->clj files))))))
    c))

;; COMPILER STUFF

(defn call-system [cmd args]
  (let [c (-> 1 async/buffer chan) 
        child (exec (str cmd " "
                         (clojure.string/join " " args))
                    (fn [err stdout stderr]
                      (go (when err (>! c [:err err]))
                          (when-not (empty? stderr)
                            (>! c [:stderr stderr]))
                          (when-not (empty? stdout)
                            (>! c [:stdout stdout])))))]  
    c))
