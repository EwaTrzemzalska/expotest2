(ns example.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
 :get-counter
 (fn [db _]
   (:counter db)))

(reg-sub
 :get-todo-list
 (fn [db]
   (:todo-list db)))

(reg-sub
 :input
 (fn [db]
   (:input db)))