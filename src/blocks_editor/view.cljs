(ns blocks-editor.view
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]
            [blocks-editor.styles :as s]))

(def menu-button-size "60px")

(defn menu-button
  [icon tip event]
  [:button (assoc (s/menu-button menu-button-size)
                  :title tip
                  :on-click #(rf/dispatch [event]))
   [:span (assoc (s/icon icon) :style {:font-size "30px"})]])

(defn ui
  []
  [:div s/body
   [:div [:div#menu s/menu
          [menu-button "glyphicon-folder-open"
           "Open file"
           :open-file]
          [menu-button "glyphicon-floppy-disk"
           "Save file"
           :save-file]
          [menu-button "glyphicon-cog"
           "Compile file"
           "Compile"
           :compile]]]
   [:div#blocklyDiv s/blockly]])

