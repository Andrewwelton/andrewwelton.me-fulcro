(ns app.ui.root
  (:require
    [fulcro.client.dom :as dom :refer [div]]
    [fulcro.client.primitives :as prim :refer [defsc]]
    [fulcro.client.routing :refer [defrouter defsc-router]]    
    [app.ui.components :as comp]))

(defsc Index [this {:keys [db/id router/page]}]
  {:initial-state {:db/id 1 :router/page :PAGE/index}}
  (div "Welcome to the home page")
)

(defsc Root [this {:keys [ui/react-key comments]}]
  {:query [:ui/react-key {:comments (prim/get-query comp/CommentList)}]
   :initial-state (fn [params] {:comments (prim/get-initial-state comp/CommentList {})})}
  (div :.ui.segments
    (div :.ui.top.attached.segment
      (div :.content
        "Welcome to Fulcro!"))
    (div :.ui.attached.segment
      (div :.content
        (comp/ui-placeholder {:w 50 :h 50})
        (div "Some content here would be nice.")
        (div "Yeah it would, wouldn't it?")
        (comp/ui-comment-list comments)))))

(defsc-router RootRouter [this {:keys [router/page db/id]}]
  {:router-id :root/router
   :ident (fn [] [page id])
   :default-route Index
   :router-targets {:PAGE/index Index}}
  (div "You shouldn't be here my friend"))
