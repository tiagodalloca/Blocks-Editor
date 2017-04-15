(ns blocks-editor.styles)

(defn col-size
  [size device]
  (str "col-" device "-" size " "))

(def blockly {:class "row"
              :style {:height "100vh"}})

(def body {:class "container-fluid"
           :style {:margin-left "2px"}})

(def menu {:class "navbar-fixed-top container-fluid"
           :style {:right "0px"
                   :left "auto"
                   :max-width "50vw"}
           :data-delay "{ \"show\": 500, \"hide\": 100 }"})

(defn menu-button
  [size]
  (let [col-size (partial col-size size)
        classes (str "btn btn-default ")]
    {:class classes
     :role "group"
     :style {:width size}
     :data-toggle "tooltip" :data-placement "bottom"}))

(defn img
  [src]
  {:class "image-responsive center-block"
   :style {:width "100%"
           :height "100%"}
   :src src})

