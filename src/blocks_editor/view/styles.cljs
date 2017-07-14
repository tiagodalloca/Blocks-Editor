(ns blocks-editor.view.styles
  (:require [blocks-editor.view.css :as scss]))

(def core-styles
  [[:body
    {:margin-left "2px"}]

   [:.be-blockly
    {:height "100vh"}]

   [:.robot-name
    {:max-width "50vw"
     :margin "3px"
     :padding "0px"}]

   [:.be-menu
    {:right "0px"
     :left "auto"
     :max-width "50vw"}

    [:button:focus
     {:outline "none"}]]])

(scss/gen-style! core-styles)

