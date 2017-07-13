(ns blocks-editor.view.css
  (:require [garden.core :refer [css]]
            [goog.style :as gs]))

(defn style! [style]
  (gs/installStyles style))

(defn gen-style! [style]
  (-> style css gs/installStyles))

;; (gen-style!
;;  [[:.blocklyToolboxDiv
;;    {:background-color "aliceblue"}]])

