(ns user
  (:require [integrant.repl :as ig-r]
            foo.bar
            foo.baz))

(comment
  ;; Basic c.t.n and integrant setup: tell it which directories to refresh, and
  ;; start a (empty) system
  (clojure.tools.namespace.repl/set-refresh-dirs "src")
  (ig-r/set-prep! #(do {}))
  (ig-r/go)

  ;; =========== The Magic: ================
  ;; Prime c.t.n at boot, only reload namespaces that have been touched after
  ;; our REPL was started.
  (alter-var-root #'clojure.tools.namespace.repl/refresh-tracker
                  assoc
                  :clojure.tools.namespace.dir/time
                  (System/currentTimeMillis))
  ;; =======================================

  ;; Now do a reset:
  (ig-r/reset)
  ;; This should output in your REPL:
  ;; :reloading ()

  ;; Instead of what you would get without the alter-var-root:
  ;; :reloading (foo.bar foo.baz user)
  )
