(ns example.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
 :get-counter
 (fn [db _]
   (:counter db)))

(reg-sub :get-todos-by-order
         (fn [{:keys [todos-by-order todos-by-id]}]
           (map (fn [id]
                  (-> todos-by-id
                      (get id)
                      (assoc :id id))) todos-by-order)))

(reg-sub
 :input
 (fn [db]
   (:input db)))