(ns blocks-editor.view.elements)

(defn col-size
  [size device]
  (str "col-" device "-" size " "))

(def blockly {:class "row be-blockly"})

(def body {:class "container-fluid"})

(def menu {:class "navbar-fixed-top container-fluid be-menu" 
           :data-delay "{ \"show\": 500, \"hide\": 100 }"})

(defn menu-button
  [size]
  (let [col-size (partial col-size size)
        classes (str "btn btn-default ")]
    {:class classes
     :role "group"
     :style {:width size}
     :data-toggle "tooltip" :data-placement "bottom"}))

(defn icon
  [i]
  {:class (str "glyphicon " i)})

(defn img
  [src]
  {:class "image-responsive center-block"
   :style {:width "100%"
           :height "100%"}
   :src src})

