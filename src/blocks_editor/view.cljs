(ns blocks-editor.view
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]
            [blocks-editor.view.elements :as ve]))

(def menu-button-size "60px")

(defn menu-button
  [icon tip event]
  [:button (assoc (ve/menu-button menu-button-size)
                  :title tip
                  :on-click #(rf/dispatch [event]))
   [:span (assoc (ve/icon icon) :style {:font-size "30px"})]])

(defn ui
  []
  [:div ve/body
   [:div [:div#menu ve/menu
          [menu-button "glyphicon-folder-open"
           "Open file"
           :open-file]
          [menu-button "glyphicon-floppy-disk"
           "Save file"
           :save-file]
          [menu-button "glyphicon-cog" 
           "Compile"
           :compile]]]
   [:div#blocklyDiv ve/blockly]])

