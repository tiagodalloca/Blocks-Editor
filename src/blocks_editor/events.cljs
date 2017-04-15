(ns ^:figwheel-load blocks-editor.events
  (:require [re-frame.core :as rf]
            [blocks-editor.core :as c]
            
            [Blockly.Xml :as bx]))

(def node-require (js* "require"))

(defonce dialog (->"electron" node-require .-remote .-dialog))
(defonce fs (node-require "fs"))

(rf/reg-event-db
 :init-db
 (fn [_ [_ v]] v))

(rf/reg-event-db
 :open-file
 (fn [db [_ _]] 
   (.showOpenDialog
    dialog
    #(when %
       (.readFile fs (aget % 0) "utf-8"
                  (fn [err content]
                    (if-not err
                      (-> content
                          bx/textToDom (bx/domToWorkspace c/workspace))
                      (js/alert (str
                                 "Sorry, could not open your file: \n"
                                 err)))))))))

(rf/reg-event-db
 :save-file
 (fn [db [_ _]] 
   (if (.-getTopBlocks c/workspace) 
     (.showSaveDialog
      dialog
      #(when %
         (.writeFile fs % (-> c/workspace
                              bx/workspaceToDom bx/domToPrettyText)
                     (fn [err]
                       (when err
                         (js/alert "Sorry, could not save your file"))))))
     (js/alert "There's no blocks to save"
               "Alert"))))

(rf/reg-event-db
 :compile
 (fn [db [_ _]] 
   (js/alert "COMPILE")))
