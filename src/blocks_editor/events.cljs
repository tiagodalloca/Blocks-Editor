(ns ^:figwheel-load blocks-editor.events
  (:require [re-frame.core :as rf]
            [blocks-editor.core :as c]
            
            [Blockly.Xml :as bx]
            [Blockly.Variables :as bv]))

(defonce node-require (js* "require"))

(defonce dialog (-> "electron" node-require .-remote .-dialog))
(defonce fs (node-require "fs"))

(rf/reg-event-db
 :init-db
 (fn [_ _]
   {:robot-name ""}))

(rf/reg-event-db
 :app-start
 (fn [_ [_ v]] v))

(rf/reg-event-db
 :update-robot-name
 (fn [db [_ v]]
   (assoc db :robot-name v)))

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
         (let [wdom (-> c/workspace bx/workspaceToDom)
               vars (.createElement js/document "shadow")
               svars  (-> c/workspace bv/allUsedVariables .join)
               tvars (.createTextNode js/document svars)]
           (doto vars
             (.appendChild tvars)
             (.setAttribute "type" "variables_used")) 
           (.insertBefore wdom vars (.-firstChild wdom))
           (.writeFile fs % (bx/domToPrettyText wdom)
                       (fn [err]
                         (when err
                           (js/alert "Sorry, could not save your file")))))))
     (js/alert "There's no blocks to save"
               "Alert"))))

(rf/reg-event-db
 :compile
 (fn [db [_ _]] 
   (js/alert "COMPILE")))

