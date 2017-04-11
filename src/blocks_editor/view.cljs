(ns blocks-editor.view
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]
            [blocks-editor.styles :as s]))

(defn menu-button
  [label size]
  [:button (s/menu-button size) label])

(defn ui
  []
  [:div {:class "container"}
   [:div
    [:div#menu s/menu
     [menu-button "Abrir" 3]
     [menu-button "Novo" 3]
     [menu-button "Salvar" 3]
     [menu-button "Compilar" 3]]]
   [:div#blocklyDiv s/blockly]])

