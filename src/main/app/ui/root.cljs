(ns app.ui.root
  (:require
   [fulcro.client.dom :as dom :refer [div h3 a]]
   [fulcro.client.primitives :as prim :refer [defsc]]
   [fulcro.client.routing :as r :refer [defsc-router]]    
   [app.ui.components :as comp]
   [app.ui.components.home :as home]))

(defsc Index [this {:keys [db/id router/page]}]
  {:query [:db/id :router/page]
   :ident (fn [] [page id])
   :initial-state {:db/id 1 :router/page :PAGE/index}}
  (home/ui-component-home))

(defsc Blog [this {:keys [db/id router/page]}]
  {:query [:db/id :router/page]
   :ident (fn [] [page id])
   :initial-state {:db/id 1 :router/page :PAGE/blog}})

(defsc Work [this {:keys [db/id router/page]}]
  {:query [:db/id :router/page]
   :ident (fn [] [page id])
   :initial-state {:db/id 1 :router/page :PAGE/work}})

(defsc-router RootRouter [this {:keys [router/page db/id]}]
  {:router-id :root/router
   :ident (fn [] [page id])
   :default-route Index
   :router-targets {:PAGE/index Index
                    :PAGE/blog Blog
                    :PAGE/work Work}}
  (div "You shouldn't be here my friend"))

(def ui-root-router (prim/factory RootRouter))

(defsc Root [this {:keys [router]}]
  {:initial-state (fn [p] {:router (prim/get-initial-state RootRouter {})})
   :query [{:router (prim/get-query RootRouter)}]}
  (div :.ui.container.grid
       (div :.row
            (div :.column
                 (h3 "Andrew Welton")))
       (div :.row
            (div :.column
                 (div :.ui.secondary.menu
                      (a :.item {:onClick #(prim/transact! this `[(r/set-route {:router :root/router :target [:PAGE/index 1]})])} "Home")
                      (a :.item {:onClick #(prim/transact! this `[(r/set-route {:router :root/router :target [:PAGE/blog 1]})])} "Blog")
                      (a :.item {:onClick #(prim/transact! this `[(r/set-route {:router :root/router :target [:PAGE/work 1]})])} "My Work"))))
       (div :.row
            (div :.column
                 (ui-root-router router)))))
