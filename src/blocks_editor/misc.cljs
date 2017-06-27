(ns blocks-editor.misc
  (:require [cljs.core.async :as async
             :refer [put! chan <! >! timeout close!]]
            [Blockly.Msg :as bmsg])
  (:require-macros [cljs.core.async.macros
                    :refer [go go-loop alt!]]))

(defonce node-require (js* "require"))
(defonce bootbox (node-require "bootbox"))
(defonce b-prompt (.-prompt bootbox))
(defonce window js/window)
(defonce goog (js* "goog"))

(.require goog "Blockly.Variables")

(defonce blockly-variables (js* "Blockly.Variables"))

(defn default-prompt [title default]
  (let [ret (chan)
        callback #(put! ret
                        (if-not (and % (empty? %))
                          % default))]
    (b-prompt title callback)
    ret))

(defn prompt-name [prompt-text default-text]
  (go (let [text (<! (default-prompt prompt-text
                                     default-text))]
        (if (or (= bmsg/RENAME_VARIABLE text)
                (= bmsg/NEW_VARIABLE text))
          nil
          text))))

(set! (.-promptName blockly-variables) prompt-name)

(defn create-variable [workspace]
  (go (loop []
        (if-some
            [text (<! (prompt-name
                       bmsg/NEW_VARIABLE_TITLE ""))]
          (if (not= -1 (.variableIndexOf workspace text))
            (do (.alert window
                        (.replace
                         bmsg/VARIABLE_ALREADY_EXISTS
                         "%1"
                         (.toLowerCase text)))
                (recur))
            (do (.createVariable workspace text)
                text))
          nil))))

(set! (.-createVariable blockly-variables) create-variable)

