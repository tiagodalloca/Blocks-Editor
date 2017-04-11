(ns blocks-editor.styles)

(def blockly {:class "row"
              :style {:height 600}})

(defn col-size
  [size device]
  (str "col-" device "-" size " "))

(def menu {:class "row"
           :style {:margin-top "15px"
                   :margin-bottom "15px"}})

(defn menu-button
  [size]
  (let [col-size (partial col-size size)
        classes (str "btn " (col-size "sm") (col-size "md"))]
    {:class classes}))

