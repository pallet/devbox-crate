(ns pallet.crate.devbox
  "A pallet crate to set up a dev box.

The local directory (including checkouts) is copied to the remote machine.

Relies on ssh-agent forwarding for access to secured repositories."
  (:require
   [clojure.java.io :refer [file]]
   [pallet.action :refer [with-action-options]]
   [pallet.actions :refer [rsync-directory]]
   [pallet.api :refer [plan-fn server-spec]]
   [pallet.crate :refer [defplan]]
   [pallet.crate.git :refer [git-cmdline clone]]
   [pallet.crate.java :refer [java]]
   [pallet.crate.lein :refer [leiningen]]))

(defplan copy-local-repo
  "Copy the local .git repo to the remote machine"
  [{:keys [local-dir remote-dir]
    :or {local-dir (str (System/getProperty "user.dir") "/")}}]
  (with-action-options
    {:script-prefix :no-sudo}
    (rsync-directory
     local-dir
     (or remote-dir (.getName (file local-dir))))))

(defn devbox-from-local
  [{:as settings}]
  (server-spec
   :extends [(git-cmdline (:git settings))
             (java (:java settings))
             (leiningen (:lein settings))]
   :phases {:configure (plan-fn
                         (copy-local-repo
                          (dissoc settings :git :java :lein)))}))

(defn devbox-from-repo
  [{:keys [repo-url] :as settings}]
  (server-spec
   :extends [(git-cmdline (:git settings))
             (java (:java settings))
             (leiningen (:lein settings))]
   :phases {:configure (plan-fn
                         (clone repo-url))}))
