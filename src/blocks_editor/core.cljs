(ns blocks-editor.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf] 
            [blocks-editor.view :as v]

            [Blockly :as b])
  (:import [Blockly.Blocks
            loops logic procedures math texts variables lists colour]
           [Blockly.Msg en]))


(re-frame.core/reg-event-db
 :new-file
 (fn [db [_ _]] 
   (js/alert "NEW FILE")))

(re-frame.core/reg-event-db
 :open-file
 (fn [db [_ _]] 
   (js/alert "OPEN FILE")))

(re-frame.core/reg-event-db
 :save-file
 (fn [db [_ _]] 
   (js/alert "SAVE FILE")))

(re-frame.core/reg-event-db
 :compile
 (fn [db [_ _]] 
   (js/alert "COMPILE")))



(defn ^:export init!
  [settings]
  (reagent/render
   [v/ui]
   (js/$ "app")) 
  (doto (.ajax js/$ (clj->js  {:url "assets/xml/toolbox.xml"}))
    (.done #(b/inject (js/$ "#blocklyDiv")
                      (clj->js {:toolbox %})))))

