(ns blocks-editor.init
  (:require [blocks-editor.core :as c]))

(set! (.-onload js/window) #(c/init! {}))

