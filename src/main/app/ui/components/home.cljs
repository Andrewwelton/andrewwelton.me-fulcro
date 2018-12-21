(ns app.ui.components.home
  (:require
   [fulcro.client.primitives :as prim :refer [defsc]]
   [fulcro.client.dom :as dom]
   ["moment" :as moment]))

(defsc Home
  "General home page component"
  [this {:keys [db/id]}]
  {:query [:db/id]
   :ident [:db/id]
   :initial-state (fn [{:keys [id]}]
                    {:db/id id})}
  (let [current-age (moment)
        please (clj->js (- (new js/Date) (new js/Date 93 3)))]
    (dom/div :.ui.container
             (dom/span "Welcome to my personal site")
             (dom/span (str "I'm a " current-age " year old programmer."))
             (dom/div (str "Capture your moment: " please)))))

(def ui-component-home (prim/factory Home))