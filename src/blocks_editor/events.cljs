(ns blocks-editor.events
  (:require [re-frame.core :as rf]
            [blocks-editor.core :as c]
            
            [Blockly.Xml :as bx]))

(rf/reg-event-db
 :init-db
 (fn [_ [_ v]] v))

(rf/reg-event-db
 :new-file
 (fn [db [_ _]] 
   (js/alert
    "NEW FILE")))

(rf/reg-event-db
 :open-file
 (fn [db [_ _]] 
   (js/alert "OPEN FILE")))

(rf/reg-event-db
 :save-file
 (fn [db [_ _]] 
   (if (.-getTopBlocks c/workspace)
     (js/alert (str "TO SAVE: \n"
                    (-> c/workspace
                        bx/workspaceToDom
                        bx/domToPrettyText)))
     (js/alert "There's no blocks to save"
               "Alert"))))

(rf/reg-event-db
 :compile
 (fn [db [_ _]] 
   (js/alert "COMPILE")))
