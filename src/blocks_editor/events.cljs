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
   {:robot-name nil}))

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
       (.readFile
        fs (aget % 0) "utf-8"
        (fn [err content]
          (if-not err
            (let [wdom (bx/textToDom content)
                  rname (some-> wdom .-children (aget 0)
                                .-innerHTML)] 
              (.clear c/workspace)
              (bx/domToWorkspace wdom c/workspace)
              (rf/dispatch [:update-robot-name rname]))
            (do (js/alert (str
                           "Sorry, could not open your file: \n"
                           err))))))))
   db))

(rf/reg-event-db
 :save-file
 (fn [db [_ _]] 
   (if (.-getTopBlocks c/workspace) 
     (.showSaveDialog
      dialog
      #(when % 
         (let [wdom (-> c/workspace bx/workspaceToDom)
               vars (.createElement js/document "meta")
               svars  (-> c/workspace bv/allUsedVariables .join)
               tvars (.createTextNode js/document svars)

               rname (.createElement js/document "meta") 
               srname (:robot-name db)
               trname (.createTextNode js/document srname)]
           
           (doto vars
             (.appendChild tvars)
             (.setAttribute "type" "variables_used"))
           (doto rname
             (.appendChild trname)
             (.setAttribute "type" "robot_name"))
           
           (.insertBefore wdom vars (.-firstChild wdom))
           (.insertBefore wdom rname vars)
           (.writeFile fs % (bx/domToPrettyText wdom)
                       (fn [err]
                         (when err
                           (js/alert "Sorry, could not save your file")))))))
     (js/alert "There's no blocks to save"
               "Alert"))
   db))

(rf/reg-event-db
 :compile
 (fn [db [_ _]] 
   (js/alert "COMPILE")
   db))

