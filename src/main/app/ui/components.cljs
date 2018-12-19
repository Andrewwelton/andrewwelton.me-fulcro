(ns app.ui.components
  (:require
    [fulcro.client.primitives :as prim :refer [defsc]]
    [fulcro.client.dom :as dom]))

(defsc PlaceholderImage
  "Generates an SVG image placeholder of the given size and with the given label
  (defaults to showing 'w x h'.

  ```
  (ui-placeholder {:w 50 :h 50 :label \"avatar\"})
  ```
  "
  [this {:keys [w h label]}]
  (let [label (or label (str w "x" h))]
    (dom/svg #js {:width w :height h}
      (dom/rect #js {:width w :height h :style #js {:fill        "rgb(200,200,200)"
                                                    :strokeWidth 2
                                                    :stroke      "black"}})
      (dom/text #js {:textAnchor "middle" :x (/ w 2) :y (/ h 2)} label))))

(def ui-placeholder (prim/factory PlaceholderImage))

(defsc Comment
  "Generates a comment component"
  [this {:keys [db/id comment/text comment/time comment/author]}]
  {:query [:db/id :comment/text :comment/time :comment/author]
   :ident [:comment/by-id :db/id]
   :initial-state (fn [{:keys [id text time author] :as params}]
    {:db/id id :comment/text text :comment/time time :comment/author author})}
  (dom/div :.comment
    (dom/div :.content
      (dom/a :.author author))
    (dom/div :.metadata
      (dom/span :.date time)
      (dom/span (str "Post #: " id)))
    (dom/div :.text
      text)
    (dom/div :.actions
      (dom/a :.reply "Reply"))))

(def ui-comment (prim/factory Comment))

(defsc CommentList
  "Generates a list of comments"
  [this {:keys [db/id commentList/comments]}]
  {:query [:db/id {:commentList/comments (prim/get-query Comment)}]
   :ident [:commentList/by-id :db/id]
   :initial-state (fn [{:keys [id]}]
    {:db/id (or id 1)
     :commentList/comments
      [(prim/get-initial-state Comment 
        {:text "Hello World, and hello friends" :time "Just Now" :id 2 :author "Mister Meekay"})
       (prim/get-initial-state Comment
        {:text "What did you say to me?" :time "Ages Ago" :id 1 :author "BaronYen"})]})}
  (dom/div :.ui.comments
    (dom/h4 :.ui.dividing.header "Comments")
    (map ui-comment comments)))

(def ui-comment-list (prim/factory CommentList))
