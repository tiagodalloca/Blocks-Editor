(ns blocks-editor-demo-front.init
    (:require [blocks-editor-demo-front.core :as core]
              [blocks-editor-demo-front.conf :as conf]))

(enable-console-print!)

(defn start-descjop! []
  (core/init! conf/setting))

(start-descjop!)
