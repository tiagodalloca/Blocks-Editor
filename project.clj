(defproject blocks-editor "0.1.0-SNAPSHOT"
  :description "An editor for visual blocks programming built with ClojureScript + Electron" 
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.473" :exclusions [org.apache.ant/ant]]
                 [re-frame "0.9.2"]]
  
  :plugins [[lein-cljsbuild "1.1.5"]
            [lein-externs "0.1.6"]
            [lein-shell "0.5.0"]
            [lein-figwheel "0.5.9" :exclusions [org.clojure/core.cache]]]

  :profiles {:dev {:dependencies [[figwheel "0.5.9"]
                                  [figwheel-sidecar "0.5.9"]
                                  [com.cemerick/piggieback "0.2.1"]
                                  [binaryage/devtools "0.9.0"]]

                   :source-paths ["src" "src_tools" "dev"]}}
  
  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

  :figwheel {:http-server-root "public"
             :ring-handler figwheel-middleware/app
             :server-port 3449}
  
  :hooks [leiningen.cljsbuild] 
  :cljsbuild {:builds
              {:release {:source-paths ["src"]
                         :compiler {:output-to "app/js/compiled/app.js"
                                    :main blocks-editor.init
                                    :optimizations :advanced}}
               
               :dev {:source-paths ["src" "dev/cljs/"]
                     :figwheel true
                     :compiler {:output-to "app/js/compiled/app.js"
                                :output-dir "app/js/compiled/out"
                                :asset-path "js/compiled/out"
                                :main user.cljs
                                :source-map-timestamp true

                                :preloads [devtools.preload]
                                :optimizations :none}}}}

  :clean-targets ["app/js/compiled/app.js"])

