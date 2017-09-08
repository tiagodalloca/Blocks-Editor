(defproject blocks-editor "0.1.0-SNAPSHOT"
  :description "An editor for visual blocks programming built with ClojureScript + Electron" 
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.473"
                  :exclusions [org.apache.ant/ant]]
                 [org.clojure/core.async "0.3.443"]
                 [re-frame "0.9.2"]
                 [garden "1.3.2"]]
  
  :plugins [[lein-cljsbuild "1.1.5"]
            [lein-externs "0.1.6"]
            [lein-shell "0.5.0"]
            [lein-figwheel "0.5.9"
             :exclusions [org.clojure/core.cache]]]

  :profiles {:dev {:dependencies [[figwheel "0.5.9"]
                                  [figwheel-sidecar "0.5.9"]
                                  [com.cemerick/piggieback "0.2.1"]
                                  [binaryage/devtools "0.9.2"]]

                   :source-paths ["src" "dev"]}}
  
  :repl-options {:nrepl-middleware
                 [cemerick.piggieback/wrap-cljs-repl]}
  
  :hooks [leiningen.cljsbuild] 
  :cljsbuild {:builds
              {:release {:source-paths ["src"]
                         :compiler {:main "blocks-editor.init"

                                    :parallel-build true
                                    :cache-analysis false
                                    :closure-defines {"goog.DEBUG" false}

                                    :output-to "app/js/compiled/app.js"
                                    :output-dir "app/js/compiled/release/out"
                                    :libs ["app/node_modules/blockly/"]
                                    :asset-path "js/compiled/release/out"
                                    :optimizations :none}}
               
               :dev {:source-paths ["src" "dev/cljs/"]
                     :figwheel {:on-jsload "cljs.user/reload"}
                     :compiler {:main "cljs.user" 
                                :output-to "app/js/compiled/app.js"
                                :output-dir "app/js/compiled/dev/out" 
                                :libs ["app/node_modules/blockly/"] 
                                :asset-path "js/compiled/dev/out"
                                :source-map-timestamp true
                                :preloads [devtools.preload]
                                :optimizations :none}}}}

  :clean-targets ^{:protect false} ["app/js/compiled"])

