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
   [:img (s/img icon)]])

(defn ui
  []
  [:div s/body
   [:div [:div#menu s/menu
          [menu-button "assets/icons/open.png"
           "Open file"
           :open-file]
          [menu-button "assets/icons/new.png"
           "New file"
           :new-file]
          [menu-button "assets/icons/save.png"
           "Save file"
           :save-file]
          [menu-button "assets/icons/compile.png"
           "Compile file"
           :compile]]]
   [:div#blocklyDiv s/blockly]])

