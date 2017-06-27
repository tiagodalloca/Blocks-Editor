(ns blocks-editor.misc
  (:require [blocks-editor.async-events :refer [subscribe-on-wload]]
            [cljs.core.async :as async
             :refer [put! chan <! >! timeout close!]]
            [Blockly.Msg :as bmsg]
            [goog.math :as math])
  (:require-macros [cljs.core.async.macros
                    :refer [go go-loop alt!]]))

(defonce node-require (js* "require"))
(defonce bootbox (node-require "bootbox"))
(defonce b-prompt (.-prompt bootbox))
(defonce window js/window)

(declare goog)
(declare blockly-variables)
(declare default-prompt)
(declare prompt-name)
(declare create-variable)

(go (<! (subscribe-on-wload)) 
    (defonce goog (js* "goog"))
    (.require goog "Blockly.Variables")
    (def a "ou")
    (defonce blockly-variables (js* "Blockly.Variables"))
    (def b "hue")
    (set! (.-promptName blockly-variables) prompt-name)
    (set! (.-createVariable blockly-variables) create-variable))


(defn default-prompt [title default]
  (let [ret (chan)
        callback #(put! ret
                        (atom (if-not (and % (empty? %))
                                %  default)))]
    (b-prompt title callback)
    ret))

(defn prompt-name [prompt-text default-text]
  (go (atom (let [text @(<! (default-prompt prompt-text
                                            default-text))]
              (if (or (= bmsg/RENAME_VARIABLE text)
                      (= bmsg/NEW_VARIABLE text))
                nil
                text)))))

(defn create-variable [workspace]
  (go (loop []
        (if-some
            [text @(<! (prompt-name
                        bmsg/NEW_VARIABLE_TITLE
                        (str "var_" (math/randomInt 99999))))]
          (if (not= -1 (.variableIndexOf workspace text))
            (do (.alert window
                        (.replace
                         bmsg/VARIABLE_ALREADY_EXISTS
                         "%1"
                         (.toLowerCase text)))
                (recur))
            (do (.createVariable workspace text)
                text))
          ""))))

