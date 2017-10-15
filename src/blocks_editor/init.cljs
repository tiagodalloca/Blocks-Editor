(ns blocks-editor.init
  (:require [blocks-editor.async-events :refer [subscribe-on-wload]]
            [blocks-editor.core :as c]
            [blocks-editor.blocks :refer [init-blocks!]]
            [blocks-editor.misc]
            [blocks-editor.events]
            
            [cljs.core.async :as async
             :refer [put! chan tap mult <! >! timeout close!]])
  (:require-macros [cljs.core.async.macros
                    :refer [go go-loop alt!]]))

(go (<! (subscribe-on-wload))
    (init-blocks!)
    (c/init! "./config.edn"))

